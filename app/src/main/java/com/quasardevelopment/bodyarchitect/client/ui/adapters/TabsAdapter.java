package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 10.06.13
 * Time: 08:06
 * To change this template use File | Settings | File Templates.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public TabsAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Parcelable saveState() {
        return super.saveState();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        TitleProvider provider= (TitleProvider) fragments.get(position);
        return provider.getTitle();
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
