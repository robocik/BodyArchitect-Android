package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.activities.ProfileInfoActivity;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.PeopleAdapter;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.UserSearchDTO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 10.06.13
 * Time: 08:11
 * To change this template use File | Settings | File Templates.
 */
public class PeopleFragment extends Fragment implements TitleProvider
{
    String title;
    PeopleAdapter adapter;
    ListView lstUsers;
    TextView tbEmpty;
    Collection<UserSearchDTO> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_people, container, false);
        lstUsers= (ListView) rootView.findViewById(R.id.lstUsers);
        lstUsers.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(),false, false));
        tbEmpty= (TextView) rootView.findViewById(R.id.tbEmpty);
        title = getArguments().getString(WorkoutPlansFragment.TITLE);
        String emptyMessage = getArguments().getString(WorkoutPlansFragment.EMPTY_MESSAGE);
        tbEmpty.setText(emptyMessage);
        adapter = new PeopleAdapter(getActivity(),R.layout.people_item,new ArrayList<UserSearchDTO>());
        fill(users);

        return rootView;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void onResume() {
        super.onResume();

        tbEmpty.setVisibility(adapter==null || adapter.getCount()==0?View.VISIBLE:View.GONE);
        lstUsers.setVisibility(adapter!=null && adapter.getCount()>0?View.VISIBLE:View.GONE);
        lstUsers.setAdapter(adapter);

        lstUsers.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent myIntent = new Intent(getActivity(), ProfileInfoActivity.class);
                UserSearchDTO item= (UserSearchDTO) lstUsers.getAdapter().getItem(i);
                myIntent.putExtra("User",item);
                startActivity(myIntent);
            }
        });
    }

    public void fill(Collection<UserSearchDTO> friends) {
        this.users=friends;
        if(adapter!=null)
        {
            adapter.clear();
            if(friends!=null)
            {
                for (UserSearchDTO user: users)
                {
                    adapter.add(user);
                }
            }

            adapter.notifyDataSetChanged();
        }
    }
}
