package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.TabsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.CustomerInfoGeneralFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.ProfileInfoSizesFragment;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.CustomerDTO;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 07.07.13
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public class CustomerInfoActivity  extends BANormalActivityBase{

    TabsAdapter mTabsAdapter;
    ViewPager pager;
    CustomerDTO user;
    CustomerInfoGeneralFragment generalFragment;
    private ProfileInfoSizesFragment sizesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_pager_layout, null);
        setMainContent(inflate);

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        generalFragment= (CustomerInfoGeneralFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 0));
        if(generalFragment==null)
        {
            generalFragment=new CustomerInfoGeneralFragment();
        }
        sizesFragment= (ProfileInfoSizesFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 1));
        if(sizesFragment==null)
        {
            sizesFragment=new ProfileInfoSizesFragment();
        }
        fragments.add(generalFragment);
        fragments.add(sizesFragment);

        pager = (ViewPager) findViewById(R.id.pager);

        mTabsAdapter=new TabsAdapter(getSupportFragmentManager(),fragments);
        pager.setAdapter(mTabsAdapter);


        PageIndicator mIndicator = (PageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);

        Intent intent=getIntent();
        user= (CustomerDTO) intent.getSerializableExtra("Customer");
        generalFragment.Fill(user);
        sizesFragment.Fill(user.wymiary);
        getSupportActionBar().setSubtitle(user.getFullName().toUpperCase());
    }

    @Override
    public void onBackPressed() {
        ApplicationState.getCurrent().CurrentViewCustomer = null;
        ApplicationState.getCurrent().setCurrentBrowsingTrainingDays( null);
        super.onBackPressed();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
