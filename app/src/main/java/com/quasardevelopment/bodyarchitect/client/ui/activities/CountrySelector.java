package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Country;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.CountryExpandableListAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 02.06.13
 * Time: 17:55
 * To change this template use File | Settings | File Templates.
 */
public class CountrySelector extends BANormalActivityBase
{

    ExpandableListView lvCountries;

    @Override
    protected void EnsureLogged()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View inflate = getLayoutInflater().inflate(R.layout.activity_exercise_selector, null);
        setMainContent(inflate);

        lvCountries= (ExpandableListView) findViewById(R.id.lvExercises);
        lvCountries.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                Country item= (Country) expandableListView.getExpandableListAdapter().getChild(groupPosition,childPosition);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Country",item);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                return true;
            }
        });
        getSupportActionBar().setSubtitle(R.string.country_selector_title);
    }

    @Override
    protected void onResume() {
        super.onResume();

        CountryExpandableListAdapter adapter = new CountryExpandableListAdapter(this,Country.getCountries());
        lvCountries.setAdapter(adapter);
    }
}
