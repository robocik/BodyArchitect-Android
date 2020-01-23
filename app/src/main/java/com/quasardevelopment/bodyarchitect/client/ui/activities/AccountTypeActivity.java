package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.TabsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.controls.AdControl;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.ChangeAccountTypeFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.MyAccountFragment;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BasicHttpBinding_IBodyArchitectAccessService;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.GetProfileInformationCriteria;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.IWsdl2CodeEvents;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.ProfileInformationDTO;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 08.07.13
 * Time: 07:23
 * To change this template use File | Settings | File Templates.
 */
public class AccountTypeActivity extends BANormalActivityBase implements IWsdl2CodeEvents {
    TabsAdapter mTabsAdapter;
    ViewPager pager;
    MyAccountFragment myAccountFragment ;
    ChangeAccountTypeFragment changeAccountFragment;
    private ProgressDialog progressDlg;
    android.view.MenuItem mnuRefresh;
    AdControl ctrlAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_pager_layout, null);
        setMainContent(inflate);

        ctrlAd= (AdControl) findViewById(R.id.ctrlAd);
        pager = (ViewPager) findViewById(R.id.pager);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        myAccountFragment= (MyAccountFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 0));
        if(myAccountFragment==null)
        {
            myAccountFragment=new MyAccountFragment();
        }
        changeAccountFragment= (ChangeAccountTypeFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 1));
        if(changeAccountFragment==null)
        {
            changeAccountFragment=new ChangeAccountTypeFragment();
        }


        fragments.add(myAccountFragment);
        fragments.add(changeAccountFragment);

        getSupportActionBar().setSubtitle(R.string.account_type_title);
        mTabsAdapter=new TabsAdapter(getSupportFragmentManager(),fragments);
        pager.setAdapter(mTabsAdapter);

        PageIndicator mIndicator = (PageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2){ }

            @Override
            public void onPageSelected(int i) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int i) {            }
        });
    }

    public void ShowChangeAccountPage() {
        pager.setCurrentItem(1);
    }

    public void ShowInfoAccountPage() {
        pager.setCurrentItem(0);
    }

    public void Refresh() {
        myAccountFragment.Refresh();
        changeAccountFragment.Refresh();
        ctrlAd.EnsureVisible();
    }

    public void refreshProfile() {
        progressDlg= BAMessageBox.ShowProgressDlg(this, R.string.progress_retrieving_profile_info);

        try{
            BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(this);
            GetProfileInformationCriteria param = new GetProfileInformationCriteria();
            service.GetProfileInformationAsync(param);

        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
            BAMessageBox.ShowToast(R.string.err_retrieving_profile_info);
        }
    }

    @Override
    public void Wsdl2CodeStartedRequest() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        ApplicationState.getCurrent().setProfileInfo((ProfileInformationDTO) Data);

        ShowInfoAccountPage();
        Refresh();
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        BAMessageBox.ShowToast(R.string.err_retrieving_profile_info);
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        hideProgress();
    }

    void hideProgress()
    {
        if(progressDlg!=null)
        {
            progressDlg.dismiss();
            progressDlg=null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        if(!ApplicationState.getCurrent().isOffline)
        {
            mnuRefresh=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_refresh);
            mnuRefresh.setIcon(getResources().getDrawable(R.drawable.navigation_refresh)) ;
//            mnuRefresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(mnuRefresh,MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item==mnuRefresh)
        {
            refreshProfile();
        }
        return true;
    }
}
