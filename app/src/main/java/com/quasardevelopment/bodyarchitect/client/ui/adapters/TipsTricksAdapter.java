package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.TipsTricksFragment;
import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 16.08.13
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */
public class TipsTricksAdapter extends FragmentPagerAdapter
{

    RSSFeed feed;
    ArrayList<RSSItem> items = new ArrayList<RSSItem>();

    public TipsTricksAdapter(FragmentManager fm, RSSFeed feed) {
        super(fm);
        this.feed=feed;
        for(RSSItem item : feed.getItems())
        {
            if(item.getCategories().contains("WP7")
              || item.getCategories().contains("Training")
              || item.getCategories().contains("Application"))
            {
                items.add(item);
            }
        }

    }

    @Override
    public Fragment getItem(int position) {
        TipsTricksFragment frm= new TipsTricksFragment();
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("Feed", items.get(position));
        bundle.putInt("Position", position);
        bundle.putInt("Count", items.size());
        frm.setArguments(bundle);
        return frm;
    }


    @Override
    public int getCount() {
        return items.size();
    }


}
