package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ExerciseCache;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsCacheBase;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.ExerciseSelectorExpandableListAdapter;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BAGlobalObject;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.ExerciseDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

import java.util.Collection;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 10.05.13
 * Time: 07:09
 * To change this template use File | Settings | File Templates.
 */

public class ExerciseSelectorActivity extends SelectorActivityBase
{
    WS_Enums.ExerciseType showOnlyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        showOnlyType= (WS_Enums.ExerciseType) intent.getSerializableExtra("ExerciseType");
        getSupportActionBar().setSubtitle(R.string.exercise_selector_title);
    }


    @Override
    protected ObjectsCacheBase GetCache() {
        return ObjectsReposidory.getCache().getExercises();
    }

    @Override
    protected void fillExercises()
    {
        ExerciseCache cache=ObjectsReposidory.getCache().getExercises();
        Collection<ExerciseDTO> selectedExercises=cache.getItems().values();
        if(showOnlyType!=null)
        {
            selectedExercises= filter(new Predicate<ExerciseDTO>() {
                public boolean apply(ExerciseDTO item) {
                    return item.exerciseType.equals(showOnlyType);
                }
            }, cache.getItems().values());
        }
        ExerciseSelectorExpandableListAdapter adapter = new ExerciseSelectorExpandableListAdapter(this,selectedExercises);
        lvExercises.setAdapter(adapter);
        if(showOnlyType!=null)
        {
            lvExercises.expandGroup(0);
        }
    }

    @Override
    public void SelectItem(BAGlobalObject item) {
        ShowExerciseDetails(item);
    }

    public void AddExercise(BAGlobalObject item)
    {
        super.SelectItem(item);
    }

    protected void ShowExerciseDetails(BAGlobalObject item)
    {
        Intent myIntent = new Intent(this, ExerciseDetailActivity.class);
        myIntent.putExtra("ItemId",item.globalId);
        this.startActivityForResult(myIntent, 1);
    }

}
