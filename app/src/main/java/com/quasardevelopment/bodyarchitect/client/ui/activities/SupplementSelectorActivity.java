package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.os.Bundle;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsCacheBase;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.Cache.SupplementsCache;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.SupplementSelectorExpandableListAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 26.05.13
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
public class SupplementSelectorActivity extends SelectorActivityBase
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setSubtitle(R.string.supplement_selector_title);
    }
    @Override
    protected ObjectsCacheBase GetCache() {
        return ObjectsReposidory.getCache().getSupplements();
    }

    @Override
    protected void fillExercises()
    {
        SupplementsCache cache=ObjectsReposidory.getCache().getSupplements();
        SupplementSelectorExpandableListAdapter adapter = new SupplementSelectorExpandableListAdapter(this,cache.getItems().values());
        lvExercises.setAdapter(adapter);
    }
}
