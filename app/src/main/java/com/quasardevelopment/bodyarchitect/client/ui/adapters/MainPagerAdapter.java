package com.quasardevelopment.bodyarchitect.client.ui.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.activities.MainActivity;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.MainMenuFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.MyProfileFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.TodayFragment;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 19.04.13
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {
    MainActivity mainActivity;

    public MainPagerAdapter(MainActivity mainActivity, FragmentManager fm) {
        super(fm);
        this.mainActivity=mainActivity;
    }

    @Override
    public Fragment getItem(int position)
    {
        if(position==0)
        {
            return new MyProfileFragment();
        }
        else if (position==1){
             return new TodayFragment();
        }
        return new MainMenuFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
        {
            return mainActivity.getString(R.string.main_activity_page_my_profile);
        }
        else if (position==1){
            return mainActivity.getString(R.string.main_activity_page_today);
        }
        return mainActivity.getString(R.string.main_activity_page_menu);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
