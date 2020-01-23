package com.quasardevelopment.bodyarchitect.client.ui.activities;

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
import com.quasardevelopment.bodyarchitect.client.ui.adapters.TabsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.PeopleFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.PeopleSearchFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.PeopleSearchResultFragment;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.WorkoutPlansFragment;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.UserSearchCriteria;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 10.06.13
 * Time: 07:53
 * To change this template use File | Settings | File Templates.
 */
public class PeopleActivity extends BANormalActivityBase
{
    TabsAdapter mTabsAdapter;
    private android.view.MenuItem mnuSearch;
    ViewPager pager;
    PeopleSearchResultFragment searchPage;

    PeopleFragment createFragment(int title,int empty_message)
    {
        PeopleFragment frmMy=new PeopleFragment();
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

        getSupportActionBar().setSubtitle(R.string.people_title);

        pager = (ViewPager) findViewById(R.id.pager);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        Fragment frmFriends=  getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 0));
        if(frmFriends==null)
        {
            frmFriends=createFragment(R.string.people_friends_fragment_title,R.string.people_friends_fragment_empty_message);
        }
        Fragment frmFollowers=  getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 1));
        if(frmFollowers==null)
        {
            frmFollowers=createFragment(R.string.people_followers_fragment_title,R.string.people_followers_fragment_empty_message);
        }
        searchPage= (PeopleSearchResultFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 2));
        if(searchPage==null && !ApplicationState.getCurrent().isOffline)
        {
            searchPage =new PeopleSearchResultFragment();
        }
        fragments.add(frmFriends);
        fragments.add(frmFollowers);
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

    private boolean isSearchSelected()
    {
        return pager.getCurrentItem()==2;
    }
    @Override
    protected void onResume() {
        super.onResume();

        ((PeopleFragment)mTabsAdapter.getItem(0)).fill(ApplicationState.getCurrent().getProfileInfo().friends);
        ((PeopleFragment)mTabsAdapter.getItem(1)).fill(ApplicationState.getCurrent().getProfileInfo().favoriteUsers);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        if(!ApplicationState.getCurrent().isOffline)
        {
            if(isSearchSelected())
            {
                mnuSearch=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_search);
                mnuSearch.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_search)) ;
//                mnuSearch.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                MenuItemCompat.setShowAsAction(mnuSearch,MenuItem.SHOW_AS_ACTION_ALWAYS);
            }

        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item==mnuSearch)
        {
            FragmentManager fm = getSupportFragmentManager();
            PeopleSearchFragment dlg = new PeopleSearchFragment();
            dlg.setPeopleActivity(this);
            dlg.setStyle(DialogFragment.STYLE_NORMAL,R.style.BADialog);
            dlg.show(fm, "fragment_edit_name");
        }

        return true;
    }


    public void DoSearch(UserSearchCriteria searchCriteria)
    {
        searchPage.DoSearch(searchCriteria);
    }
}
