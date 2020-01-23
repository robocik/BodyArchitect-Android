package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.TipsTricksAdapter;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.WcfConstans;


import org.mcsoxford.rss.RSSConfig;
import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSParser;
import org.mcsoxford.rss.RSSParserSPI;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 16.08.13
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class TipsTricksActivity extends BANormalActivityBase {
    ViewPager pager;
    private ProgressDialog dlg;
    private TipsTricksAdapter mAdapter;
    TextView tbStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_tips_tricks, null);
        setMainContent(inflate);

        pager= (ViewPager) findViewById(R.id.pager);
        tbStatus= (TextView) findViewById(R.id.tbStatus);
        getSupportActionBar().setSubtitle(R.string.tips_tricks_title);

    }

    String getFeedUrl()
    {
        if (WcfConstans.getCurrentServiceLanguage().equals("pl"))
        {
            return "http://service.bodyarchitectonline.com/Rss/tips_pl.rss";
        }
        return "http://service.bodyarchitectonline.com/Rss/tips_pl.rss";
    }

    public static File getFilename(Context ctx)
    {
        File dir=ctx.getDir("BA", Context.MODE_PRIVATE);
        File tipsFile=new File(dir,String.format("tips.%s",WcfConstans.getCurrentServiceLanguage()));
        return tipsFile;
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

        File file = getFilename(this);
        if(file.exists())
        {
            fillTips(file);
        }
        else
        {
            if(ApplicationState.getCurrent().isOffline)
            {
                tbStatus.setText(R.string.info_feature_offline_mode);
            }
            else
            {
                dlg=BAMessageBox.ShowProgressDlg(this,R.string.progress_retrieving_items);
                DownloadFile downloadFile = new DownloadFile();
                downloadFile.execute(getFeedUrl());
            }
        }
    }


    private void fillTips(File file) {
        if(!file.exists())
        {
            return;
        }
        try {
            FileInputStream stream = new FileInputStream(file);
            RSSConfig config=new RSSConfig();
            RSSParserSPI reader = new RSSParser(config);
            RSSFeed feed = reader.parse(stream);
            mAdapter = new TipsTricksAdapter(getSupportFragmentManager(),feed);
            pager.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            Random rand = new Random();
            pager.setCurrentItem(rand.nextInt(mAdapter.getCount()-1));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            tbStatus.setText(R.string.err_unhandled);
        }
    }

    // usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
    private class DownloadFile extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... sUrl) {
            try {
                URL url = new URL(sUrl[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // this will be useful so that you can show a typical 0-100% progress bar
                int fileLength = connection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());

                OutputStream output = new FileOutputStream(getFilename(TipsTricksActivity.this));

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
                return true;
            } catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            if(result)
            {
                tbStatus.setText("");
                File file = getFilename(TipsTricksActivity.this);
                fillTips(file);

            }
            else
            {
                tbStatus.setText(R.string.err_unhandled);
            }
            if(dlg!=null)
            {
                dlg.dismiss();
                dlg=null;
            }
        }
    }
}
