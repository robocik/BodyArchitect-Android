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
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsCacheBase;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.TabsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.BodyInstructorMoreFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.CustomersFragment;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.IWsdl2CodeEvents;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 05.07.13
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class BodyInstructorActivity extends BANormalActivityBase implements IWsdl2CodeEvents {
    private ViewPager pager;
    private TabsAdapter mTabsAdapter;
    private ProgressDialog progressDlg;
    CustomersFragment customersFragment;
    private android.view.MenuItem mnuRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_pager_layout, null);
        setMainContent(inflate);

        pager = (ViewPager) findViewById(R.id.pager);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        customersFragment= (CustomersFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 0));
        if(customersFragment==null)
        {
            customersFragment=new CustomersFragment();
        }
        Fragment bodyInstructorMoreFragment=  getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 1));
        if(bodyInstructorMoreFragment==null)
        {
            bodyInstructorMoreFragment=new BodyInstructorMoreFragment();
        }

        customersFragment = new CustomersFragment();
        fragments.add(customersFragment);
        fragments.add(bodyInstructorMoreFragment);

        mTabsAdapter=new TabsAdapter(getSupportFragmentManager(),fragments);
        pager.setAdapter(mTabsAdapter);

        PageIndicator mIndicator = (PageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
        getSupportActionBar().setSubtitle(R.string.bodyinstructor_title);

    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshItems();
    }

    private void refreshItems() {
        ObjectsCacheBase cache= ObjectsReposidory.getCache().getCustomers();


        if(cache.isLoaded())
        {
            fillCustomers();
        }
        else
        {
            progressDlg= BAMessageBox.ShowProgressDlg(this, R.string.progress_retrieving_items);
            cache.LoadAsync(this);
        }
    }

    protected void closeProgress()
    {
        if(progressDlg!=null)
        {
            progressDlg.dismiss();
            progressDlg=null;
        }
    }

    void fillCustomers()
    {


        customersFragment.setItems();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        if(!ApplicationState.getCurrent().isOffline )
        {

                mnuRefresh=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_refresh);
                mnuRefresh.setIcon(getResources().getDrawable(R.drawable.navigation_refresh)) ;
//                mnuRefresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

            MenuItemCompat.setShowAsAction(mnuRefresh,MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item==mnuRefresh)
        {
            ObjectsCacheBase cache= ObjectsReposidory.getCache().getCustomers();
            cache.Clear();
            refreshItems();
        }
        return true;
    }

    @Override
    public void Wsdl2CodeStartedRequest() {}

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        fillCustomers();
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        BAMessageBox.ShowToast(R.string.bodyinstructor_err_retrieve_customers);
        customersFragment.setEmptyMessage(getString(R.string.bodyinstructor_err_retrieve_customers));
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        closeProgress();
    }
}
