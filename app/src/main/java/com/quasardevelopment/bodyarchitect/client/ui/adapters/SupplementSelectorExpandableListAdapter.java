package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SuplementDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 26.05.13
 * Time: 20:27
 * To change this template use File | Settings | File Templates.
 */
public class SupplementSelectorExpandableListAdapter extends BaseExpandableListAdapter
{
    ArrayList<ItemCategory<SuplementDTO>> exercises;
    private Context context;

    public SupplementSelectorExpandableListAdapter(Context context, Collection<SuplementDTO> items)
    {
        ArrayList<SuplementDTO> supplementList = new ArrayList<SuplementDTO>(items);
        Collections.sort(supplementList, new Comparator<SuplementDTO>() {
            @Override
            public int compare(SuplementDTO sup1, SuplementDTO sup2) {
                return sup1.name.compareToIgnoreCase(sup2.name);
            }
        });

        exercises=new ArrayList<ItemCategory<SuplementDTO>>();
        this.context=context;
        for (final SuplementDTO ex : supplementList)
        {
            final String firstLetter=GetFirstNameKey(ex);
            ItemCategory<SuplementDTO> category= Helper.SingleOrDefault(filter(new Predicate<ItemCategory<SuplementDTO>>() {
                public boolean apply(ItemCategory<SuplementDTO> item) {
                    return item.category.equals(firstLetter);
                }
            }, exercises));

            if(category==null)
            {
                category=new ItemCategory<SuplementDTO>();
                category.category=firstLetter;
                exercises.add(category);
            }
            category.items.add(ex);
        }
    }


    @Override
    public int getGroupCount() {
        return exercises.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return exercises.get(i).items.size();
    }

    @Override
    public Object getGroup(int i) {
        return exercises.get(i);
    }

    @Override
    public Object getChild(int i, int i2) {
        return exercises.get(i).items.get(i2);
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
        ItemCategory<SuplementDTO> group = (ItemCategory<SuplementDTO>) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.expandable_exercise_group_item, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.elv_exercise_group_tb);
        tv.setText(group.category);
        return view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        final SuplementDTO child = (SuplementDTO) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.expandable_supplement_child_item, null);
        }
        TextView tbExerciseName = (TextView) view.findViewById(R.id.elv_supplement_name);

        tbExerciseName.setText(child.name);
        return view;

    }


    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public static String GetFirstNameKey(SuplementDTO suplement)
    {
        char key = Character.toLowerCase(suplement.name.charAt(0));

        if (key < 'a' || key > 'z')
        {
            key = '#';
        }

        return Character.toString(key);
    }
}
