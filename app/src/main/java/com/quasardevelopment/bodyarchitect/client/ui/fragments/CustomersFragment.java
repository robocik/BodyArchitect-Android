package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsCacheBase;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.activities.CustomerInfoActivity;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.CustomersAdapter;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.CustomerDTO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 06.07.13
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
public class CustomersFragment extends android.support.v4.app.Fragment implements TitleProvider
{

    CustomersAdapter adapter;
    ListView lstUsers;
    TextView tbEmpty;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new CustomersAdapter(getActivity(),R.layout.customer_item,new ArrayList<CustomerDTO>());
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_people, container, false);
        lstUsers= (ListView) rootView.findViewById(R.id.lstUsers);
        lstUsers.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(),false, false));
        tbEmpty= (TextView) rootView.findViewById(R.id.tbEmpty);
        tbEmpty.setText(R.string.customers_fragment_empty_customers);

        lstUsers.setAdapter(adapter);

        lstUsers.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent myIntent = new Intent(getActivity(), CustomerInfoActivity.class);
                CustomerDTO item= (CustomerDTO) lstUsers.getAdapter().getItem(i);
                myIntent.putExtra("Customer",item);
                ApplicationState.getCurrent().CurrentViewCustomer=item;
                startActivity(myIntent);
            }
        });
        tbEmpty.setVisibility(adapter == null || adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
        lstUsers.setVisibility(adapter!=null && adapter.getCount()>0?View.VISIBLE:View.GONE);
        setItems();
        return rootView;
    }

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getResources(). getString(R.string.customers_fragment_title);
    }



    public void setItems() {

        if(adapter!=null)
        {
            ObjectsCacheBase cache= ObjectsReposidory.getCache().getCustomers();
            if(cache.isLoaded())
            {
            Collection<CustomerDTO> customers=cache.getItems().values();
            adapter.clear();
            for (CustomerDTO entry:customers)
            {
                adapter.add( entry);
            }
            adapter.notifyDataSetChanged();
                tbEmpty.setVisibility(adapter==null || adapter.getCount()==0?View.VISIBLE:View.GONE);
                lstUsers.setVisibility(adapter!=null && adapter.getCount()>0?View.VISIBLE:View.GONE);
            }
        }

    }

    public void setEmptyMessage(String message) {
        tbEmpty.setText(message);
    }
}