package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.quasardevelopment.bodyarchitect.client.ui.fragments.WorkoutPlansFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.WorkoutPlansSearchFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.WorkoutPlansSearchResultFragment;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.IWsdl2CodeEvents;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingPlan;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WorkoutPlanSearchCriteria;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 01.07.13
 * Time: 08:54
 * To change this template use File | Settings | File Templates.
 */
public class WorkoutPlansActivity extends BANormalActivityBase implements IWsdl2CodeEvents {
    private ViewPager pager;
    private TabsAdapter mTabsAdapter;
    private ProgressDialog progressDlg;
    WorkoutPlansSearchResultFragment searchPage;
    private android.view.MenuItem mnuSearch;
    private android.view.MenuItem mnuRefresh;

    WorkoutPlansFragment createFragment(int title,int empty_message)
    {
        WorkoutPlansFragment frmMy=new WorkoutPlansFragment();
        Bundle bundle = new Bundle(2);
        bundle.putString(WorkoutPlansFragment.TITLE, getString(title));
        bundle.putString(WorkoutPlansFragment.EMPTY_MESSAGE ,getString(empty_message));
        frmMy.setArguments(bundle);
        return frmMy;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_pager_layout, null);
        setMainContent(inflate);

        getSupportActionBar().setSubtitle(R.string.workout_plans_title);
        pager = (ViewPager) findViewById(R.id.pager);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        Fragment frmMy=  getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 0));
        if(frmMy==null)
        {
            frmMy=createFragment(R.string.workout_plans_header_my,R.string.workout_plans_my_empty_message);
        }
        Fragment frmFavorites=  getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 1));
        if(frmFavorites==null)
        {
            frmFavorites=createFragment(R.string.workout_plans_header_favorites,R.string.workout_plans_favorites_empty_message);
        }
        fragments.add(frmMy);
        fragments.add(frmFavorites);
        searchPage= (WorkoutPlansSearchResultFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 2));
        if(searchPage==null && !ApplicationState.getCurrent().isOffline)
        {
            searchPage =new WorkoutPlansSearchResultFragment();

        }
        if(!ApplicationState.getCurrent().isOffline)
        {
            fragments.add(searchPage);
        }


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

    @Override
    protected void onResume() {
        super.onResume();
        refreshItems();
    }

    private void refreshItems() {
        ObjectsCacheBase cache= ObjectsReposidory.getCache().getTrainingPlans();


        if(cache.isLoaded() || ApplicationState.getCurrent().isOffline)
        {
            fillPlans();
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

    void fillPlans()
    {
        ObjectsCacheBase cache= ObjectsReposidory.getCache().getTrainingPlans();
        if(cache.isLoaded())
        {
            Collection<TrainingPlan> plans=cache.getItems().values();
            ArrayList<TrainingPlan> myPlans = new ArrayList<TrainingPlan>();
            ArrayList<TrainingPlan> favoritesPlans = new ArrayList<TrainingPlan>();
            for(TrainingPlan plan : plans)
            {
                 if(plan.isMine())
                 {
                     myPlans.add(plan);
                 }
                else
                 {
                     favoritesPlans.add(plan);
                 }
            }
            ((WorkoutPlansFragment)mTabsAdapter.getItem(0)).setItems(myPlans);
            ((WorkoutPlansFragment)mTabsAdapter.getItem(1)).setItems(favoritesPlans);
        }
        else
        {
            ((WorkoutPlansFragment)mTabsAdapter.getItem(0)).setItems(null);
            ((WorkoutPlansFragment)mTabsAdapter.getItem(1)).setItems(null);
        }
    }

    @Override
    public void Wsdl2CodeStartedRequest() {}

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        fillPlans();
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        BAMessageBox.ShowToast(R.string.workout_plans_err_retrieve_plans);
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        closeProgress();
    }

    private boolean isSearchSelected()
    {
        return pager.getCurrentItem()==2;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        if(!ApplicationState.getCurrent().isOffline )
        {
            if(isSearchSelected())
            {
                mnuSearch=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_search);
                mnuSearch.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_search)) ;
//                mnuSearch.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                MenuItemCompat.setShowAsAction(mnuSearch,MenuItem.SHOW_AS_ACTION_ALWAYS);
            }
            else
            {
                mnuRefresh=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_refresh);
                mnuRefresh.setIcon(getResources().getDrawable(R.drawable.navigation_refresh)) ;
//                mnuRefresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                MenuItemCompat.setShowAsAction(mnuRefresh,MenuItem.SHOW_AS_ACTION_ALWAYS);
            }

        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item==mnuSearch)
        {
            FragmentManager fm = getSupportFragmentManager();
            WorkoutPlansSearchFragment dlg = new WorkoutPlansSearchFragment();
            dlg.setStyle(DialogFragment.STYLE_NORMAL,R.style.BADialog);
            dlg.show(fm, "fragment_edit_name");
        }
        if(item==mnuRefresh)
        {
            ObjectsCacheBase cache= ObjectsReposidory.getCache().getTrainingPlans();
            cache.Clear();
            refreshItems();
        }
        return true;
    }


    public void DoSearch(WorkoutPlanSearchCriteria searchCriteria)
    {
        searchPage.DoSearch(searchCriteria);
    }
}
