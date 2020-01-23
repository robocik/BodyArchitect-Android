package com.quasardevelopment.bodyarchitect.client.ui;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.util.BAImageDownloader;
import com.quasardevelopment.bodyarchitect.client.util.Constants;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 15.04.13
 * Time: 20:10
 * To change this template use File | Settings | File Templates.
 */
public class MyApplication extends Application {

    private static Context context;
    public Drawable  normalBgImg;
    Bitmap bitmap;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
    @Override
    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();

        Constants.IsDebugMode =( 0 != ( getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE ) ) ;

        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(this.getAppContext());
        BAImageDownloader baImageDownloader = new BAImageDownloader(getAppContext());
        builder.imageDownloader(baImageDownloader) ;
        ImageLoaderConfiguration config = builder.build();
        ImageLoader imgLoader=ImageLoader.getInstance();
        imgLoader.init(config);

        try
        {
            Constants.Version=this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        ensureImageLoaded();

    }



    public void ensureImageLoaded()
    {
        if(normalBgImg==null)
        {
            loadBitmap();
        }
    }

    public void clearResources()
    {
         if(bitmap!=null)
         {
             bitmap.recycle();
             bitmap=null;
         }
        normalBgImg=null;
    }

    void loadBitmap()
    {
        try
        {
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPurgeable=true;
            options.inInputShareable=true;
            Bitmap.Config mBitmapConfig;
            mBitmapConfig = Bitmap.Config.RGB_565;
            options.inPreferredConfig=mBitmapConfig;
            Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.page_bg,options);
            bitmap= Bitmap.createScaledBitmap(original, display.getWidth(),  display.getHeight(), false);
            normalBgImg = new BitmapDrawable(getResources(),bitmap);
            normalBgImg.setAlpha(100);
            if (original != bitmap)
                original.recycle();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public static void updateLanguage(Context context) {
        String language= Settings.getLanguage();
        if (!TextUtils.isEmpty(language)) {

            Locale locale = new Locale(Settings.getLanguage());
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = Locale.getDefault();
            context.getResources().updateConfiguration(config, null);
        }
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
