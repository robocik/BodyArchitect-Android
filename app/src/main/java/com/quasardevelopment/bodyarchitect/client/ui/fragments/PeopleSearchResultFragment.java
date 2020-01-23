package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.activities.ProfileInfoActivity;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.PeopleAdapter;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 11.06.13
 * Time: 08:44
 * To change this template use File | Settings | File Templates.
 */
public class PeopleSearchResultFragment extends Fragment implements TitleProvider , AbsListView.OnScrollListener
{
    PeopleAdapter adapter;
    ListView lstUsers;
    TextView tbEmpty;
    LinearLayout progressPane;
    PagedResultOfUserSearchDTO5oAtqRlh result;
    UserSearchCriteria searchCriteria;
    boolean isLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_people_search_result, container, false);
        lstUsers= (ListView) rootView.findViewById(R.id.lstUsers);
        tbEmpty= (TextView) rootView.findViewById(R.id.tbEmpty);
        progressPane = (LinearLayout)rootView.findViewById(R.id.progressPane);

        lstUsers.setOnScrollListener(this);
        lstUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(getActivity(), ProfileInfoActivity.class);
                UserSearchDTO item = (UserSearchDTO) lstUsers.getAdapter().getItem(i);
                myIntent.putExtra("User", item);
                startActivity(myIntent);
            }
        });
        return rootView;
    }

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getResources().getString(R.string.people_search_fragment_title);
    }

    @Override
    public void onResume() {
        super.onResume();

        tbEmpty.setVisibility(adapter==null || adapter.getCount()==0?View.VISIBLE:View.GONE);
        lstUsers.setVisibility(adapter!=null && adapter.getCount()>0?View.VISIBLE:View.GONE);
        lstUsers.setAdapter(adapter);
        lstUsers.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(),false, false));
    }


    void showProgress(boolean show)
    {
        progressPane.setVisibility(show?View.VISIBLE:View.GONE);
        tbEmpty.setVisibility(show|| (adapter!=null && adapter.getCount()>0)?View.GONE:View.VISIBLE);
        lstUsers.setVisibility(adapter==null || adapter.getCount()==0?View.GONE:View.VISIBLE);
        isLoading=show;
    }

    public void DoSearch(UserSearchCriteria searchCriteria) {
        this.searchCriteria=searchCriteria;
        BasicHttpBinding_IBodyArchitectAccessService service =new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
            @Override
            public void Wsdl2CodeStartedRequest() {
                showProgress(true);
            }

            @Override
            public void Wsdl2CodeFinished(String methodName, Object Data) {

                if(!isAdded())
                {//to prevent this type of error:
                    //java.lang.IllegalStateException: Fragment WorkoutPlansSearchResultFragment{4274e448} not attached to Activity
                    return;
                }
                result=(PagedResultOfUserSearchDTO5oAtqRlh)Data;

                adapter = new PeopleAdapter(getActivity(),R.layout.people_item,result.items);
                lstUsers.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                showProgress(false);
            }

            @Override
            public void Wsdl2CodeFinishedWithException(Exception ex) {
                showProgress(false);
                BAMessageBox.ShowToast(R.string.people_err_retrieve_users);
            }

            @Override
            public void Wsdl2CodeEndedRequest()
            {            }
        }) ;
        PartialRetrievingInfo pageInfo = new PartialRetrievingInfo();
        pageInfo.pageIndex = 0;

        try {
            service.GetUsersAsync(searchCriteria,pageInfo);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            BAMessageBox.ShowToast(R.string.people_err_retrieve_users);
            showProgress(false);
        }


    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {
        boolean loadMore = /* maybe add a padding */
                firstVisible + visibleCount >= totalCount;

        if(!isLoading && loadMore && hasMore()) {
            LoadMore();
        }
    }

    private void LoadMore() {
        BasicHttpBinding_IBodyArchitectAccessService service =new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
            @Override
            public void Wsdl2CodeStartedRequest() {
                showProgress(true);
            }

            @Override
            public void Wsdl2CodeFinished(String methodName, Object Data) {

                if(!isAdded())
                {//to prevent this type of error:
                    //java.lang.IllegalStateException: Fragment WorkoutPlansSearchResultFragment{4274e448} not attached to Activity
                    return;
                }
                result=(PagedResultOfUserSearchDTO5oAtqRlh)Data;

                PeopleAdapter adapter=(PeopleAdapter)lstUsers.getAdapter();
                for (UserSearchDTO entry:result.items)
                {
                    adapter.add( entry);
                }
                adapter.notifyDataSetChanged();
                showProgress(false);
            }

            @Override
            public void Wsdl2CodeFinishedWithException(Exception ex) {
                showProgress(false);
                BAMessageBox.ShowToast(R.string.people_err_retrieve_users);
            }

            @Override
            public void Wsdl2CodeEndedRequest()
            {
            }
        }) ;
        PartialRetrievingInfo pageInfo = new PartialRetrievingInfo();
        pageInfo.pageIndex=result.pageIndex+1;

        try {
            service.GetUsersAsync(searchCriteria,pageInfo);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            BAMessageBox.ShowToast(R.string.people_err_retrieve_users);
            showProgress(false);
        }
    }

    private boolean hasMore()
    {
        PeopleAdapter adapter=(PeopleAdapter)lstUsers.getAdapter();
        return result!=null && adapter!=null && adapter.getCount()<result.allItemsCount;
    }
}
