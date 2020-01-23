package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.TabsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.ProfileInfoAboutFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.ProfileInfoGeneralFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.ProfileInfoSizesFragment;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import com.splunk.mint.Mint;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 12.06.13
 * Time: 07:04
 * To change this template use File | Settings | File Templates.
 */
public class ProfileInfoActivity  extends BANormalActivityBase{

    TabsAdapter mTabsAdapter;
    ViewPager pager;
    UserSearchDTO user;
    ProfileInformationDTO profileInfo;
    ProgressDialog progressDialog;
    ProfileInfoAboutFragment aboutFragment;
    ProfileInfoSizesFragment sizesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_pager_layout, null);
        setMainContent(inflate);

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        ProfileInfoGeneralFragment generalFragment= (ProfileInfoGeneralFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 0));
        if(generalFragment==null)
        {
            generalFragment=new ProfileInfoGeneralFragment();
        }
        aboutFragment= (ProfileInfoAboutFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 1));
        if(aboutFragment==null)
        {
            aboutFragment=new ProfileInfoAboutFragment();
        }
        sizesFragment= (ProfileInfoSizesFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 2));
        if(sizesFragment==null)
        {
            sizesFragment=new ProfileInfoSizesFragment();
        }

        fragments.add(generalFragment);
        fragments.add(aboutFragment);
        fragments.add(sizesFragment);

        pager = (ViewPager) findViewById(R.id.pager);

        mTabsAdapter=new TabsAdapter(getSupportFragmentManager(),fragments);
        pager.setAdapter(mTabsAdapter);


        PageIndicator mIndicator = (PageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {}

            @Override
            public void onPageSelected(int i) {
                if(i!=0)
                {
                    showProfileDetails();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });

        Intent intent=getIntent();
        user= (UserSearchDTO) intent.getSerializableExtra("User");
        generalFragment.Fill(user);
        getSupportActionBar().setSubtitle(user.userName.toUpperCase());
    }

    void hideProgress()
    {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
            progressDialog=null;
        }
    }

    private void showProfileDetails() {
        if(profileInfo==null)
        {
            if(ApplicationState.getCurrent().isOffline)
            {
                aboutFragment.Fill(null);
                sizesFragment.Fill(null);
                return;
            }
            BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
                @Override
                public void Wsdl2CodeStartedRequest() {
                    progressDialog= BAMessageBox.ShowProgressDlg(ProfileInfoActivity.this,R.string.progress_retrieving_profile_info);
                }

                @Override
                public void Wsdl2CodeFinished(String methodName, Object Data) {
                    profileInfo= (ProfileInformationDTO) Data;
                    aboutFragment.Fill(profileInfo);
                    sizesFragment.Fill(profileInfo.wymiary);
                }

                @Override
                public void Wsdl2CodeFinishedWithException(Exception ex) {
                    Mint.logException(ex);
                    BAMessageBox.ShowToast(R.string.err_retrieving_profile_info);
                }

                @Override
                public void Wsdl2CodeEndedRequest() {
                    hideProgress();
                }
            }) ;

            GetProfileInformationCriteria data = new GetProfileInformationCriteria();
            data.userId = user.globalId;
            service.GetProfileInformationAsync(data);
        }
        else
        {
            aboutFragment.Fill(profileInfo);
            sizesFragment.Fill(profileInfo.wymiary);
        }
    }

    @Override
    public void onBackPressed()
    {
        ApplicationState.getCurrent().setCurrentBrowsingTrainingDays(null);
        super.onBackPressed();
    }


}
