package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.TabsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.SettingsGeneralFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.SettingsMiscFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.SettingsStrengthTrainingFragment;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 20.04.13
 * Time: 11:39
 * To change this template use File | Settings | File Templates.
 */
public class SettingsActivity extends BANormalActivityBase
{
    private TabsAdapter mTabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_pager_layout, null);
        setMainContent(inflate);
        getSupportActionBar().setSubtitle(R.string.settings_activity_title);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        Fragment generalFragment=  getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 0));
        if(generalFragment==null)
        {
            generalFragment=new SettingsGeneralFragment();
        }
        Fragment strengthTrainingFragment=  getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 1));
        if(strengthTrainingFragment==null)
        {
            strengthTrainingFragment=new SettingsStrengthTrainingFragment();
        }
        Fragment miscFragment=  getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 2));
        if(miscFragment==null)
        {
            miscFragment=new SettingsMiscFragment();
        }
        fragments.add(generalFragment);
        fragments.add(strengthTrainingFragment);
        fragments.add(miscFragment);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        mTabsAdapter=new TabsAdapter(getSupportFragmentManager(),fragments);
        pager.setAdapter(mTabsAdapter);


        TitlePageIndicator mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
        Helper.prepare(mIndicator);
    }



}
