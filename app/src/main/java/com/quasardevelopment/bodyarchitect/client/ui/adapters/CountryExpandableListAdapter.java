package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Country;
import com.quasardevelopment.bodyarchitect.client.util.Helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 03.06.13
 * Time: 07:35
 * To change this template use File | Settings | File Templates.
 */
public class CountryExpandableListAdapter extends BaseExpandableListAdapter
{

    ArrayList<ItemCategory<Country>> countries;
    private Context context;

    public CountryExpandableListAdapter(Context context, Collection<Country> items)
    {
        ArrayList<Country> countryItems = new ArrayList<Country>(items);
        Collections.sort(countryItems, new Comparator<Country>() {
            @Override
            public int compare(Country sup1, Country sup2) {
                return sup1.EnglishName.compareToIgnoreCase(sup2.EnglishName);
            }
        });

        countries=new ArrayList<ItemCategory<Country>>();
        this.context=context;
        for (final Country ex : countryItems)
        {
            final String firstLetter=GetFirstNameKey(ex);
            ItemCategory<Country> category= Helper.SingleOrDefault(filter(new Predicate<ItemCategory<Country>>() {
                public boolean apply(ItemCategory<Country> item) {
                    return item.category.equals(firstLetter);
                }
            }, countries));

            if(category==null)
            {
                category=new ItemCategory<Country>();
                category.category=firstLetter;
                countries.add(category);
            }
            category.items.add(ex);
        }
    }


    @Override
    public int getGroupCount() {
        return countries.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return countries.get(i).items.size();
    }

    @Override
    public Object getGroup(int i) {
        return countries.get(i);
    }

    @Override
    public Object getChild(int i, int i2) {
        return countries.get(i).items.get(i2);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        ItemCategory<Country> group = (ItemCategory<Country>) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.expandable_exercise_group_item, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.elv_exercise_group_tb);
        tv.setText(group.category.toString());
        return view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        final Country child = (Country) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.expandable_supplement_child_item, null);
        }
        TextView tbExerciseName = (TextView) view.findViewById(R.id.elv_supplement_name);

        tbExerciseName.setText(String.format("%s (%s)",child.EnglishName,child.NativeName));

        return view;

    }


    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public static String GetFirstNameKey(Country country)
    {
        char key = Character.toLowerCase(country.EnglishName.charAt(0));

        if (key < 'a' || key > 'z')
        {
            key = '#';
        }

        return Character.toString(key);
    }
}
