package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.GPSPointsBag;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDayInfo;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 22.07.13
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
public class SynchronizationItemsAdapter extends ArrayAdapter<SynchronizationItem>
{
    ArrayList<SynchronizationItem> items;

    public SynchronizationItemsAdapter(Context context, int textViewResourceId,List<TrainingDayInfo> entries) {
        super(context, textViewResourceId, new ArrayList<SynchronizationItem>());
        items =new ArrayList<SynchronizationItem>();
        for (TrainingDayInfo day : entries)
        {
            SynchronizationItem item = new SynchronizationItem(day);
            items.add(item);
            add(item);
        }

        fillGpsCoordinates(entries);
    }

    private void fillGpsCoordinates(List<TrainingDayInfo> modifiedEntries){

        //var notSavedGpsCoordinates=modifiedEntries.SelectMany(x => x.GPSCoordinates).Where(x => !x.Value.IsSaved).ToList();
        ArrayList<Map.Entry<LocalObjectKey,GPSPointsBag>>  notSavedGpsCoordinates = new ArrayList<Map.Entry<LocalObjectKey, GPSPointsBag>>();
         for (TrainingDayInfo info : modifiedEntries)
         {
             List<Map.Entry<LocalObjectKey,GPSPointsBag>> modifiedGpsPoints= filter(new Predicate<Map.Entry<LocalObjectKey,GPSPointsBag>>() {
                 public boolean apply(Map.Entry<LocalObjectKey,GPSPointsBag> item) {
                     return !item.getValue().IsSaved;
                 }
             }, info.getGPSCoordinates().entrySet());

             notSavedGpsCoordinates.addAll(modifiedGpsPoints);
         }

        for (final Map.Entry<LocalObjectKey,GPSPointsBag> info : notSavedGpsCoordinates)
        {
            //var tdi=modifiedEntries.SingleOrDefault(x => x.GPSCoordinates.ContainsKey(info.Key));
            final TrainingDayInfo tdi= Helper.SingleOrDefault(filter(new Predicate<TrainingDayInfo>() {
                public boolean apply(TrainingDayInfo item) {
                    return item.getGPSCoordinates().containsKey(info.getKey()) ;
                }
            }, modifiedEntries));
            //var entry=tdi.TrainingDay.Objects.OfType<GPSTrackerEntryDTO>().SingleOrDefault(x => x.GlobalId == info.Key.Id || x.InstanceId == info.Key.Id);

            GPSTrackerEntryDTO entry= (GPSTrackerEntryDTO) Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
                public boolean apply(EntryObjectDTO x) {
                    return x.globalId.equals(info.getKey().getId()) || x.instanceId.equals(info.getKey().getId());
                }
            }, tdi.getTrainingDay().objects));
            if(entry!=null)
            {
                SynchronizationItem item = new SynchronizationItem(tdi,entry,info.getValue());
                items.add(item);
                add(item);
            }
        }

    }


    public ArrayList<SynchronizationItem> getItems()
    {
        return items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.synchronization_item, parent, false);
        }

        SynchronizationItem lap=getItem(position);

        LinearLayout pnlSyncState= (LinearLayout) row.findViewById(R.id.pnlSyncState);
        TextView tbDateTime= (TextView) row.findViewById(R.id.tbDateTime);
        TextView tbStatus= (TextView) row.findViewById(R.id.tbStatus);
        TextView tbGpsCoordinates= (TextView) row.findViewById(R.id.tbGpsCoordinates);

        tbGpsCoordinates.setVisibility(lap.Type.equals(SynchronizationItem.ItemType.GPSCoordinates)?View.VISIBLE:View.GONE);
        LinearLayout pnlEntries= (LinearLayout) row.findViewById(R.id.pnlEntries);

        tbDateTime.setText(lap.Day.getTrainingDay().trainingDate.toString(DateTimeFormat.shortDate()));

        if(lap.State.equals(SynchronizationItem.MergeState.None))
        {
            pnlSyncState.setBackgroundColor(Color.GRAY);
        }
        else if (lap.State.equals(SynchronizationItem.MergeState.Error))
        {
            pnlSyncState.setBackgroundColor(Color.RED);
        }
        else if (lap.State.equals(SynchronizationItem.MergeState.Processing))
        {
            pnlSyncState.setBackgroundColor(Color.GREEN);
        }
        tbStatus.setText(EnumLocalizer.Translate(lap.State));

        if(!lap.Type.equals(SynchronizationItem.ItemType.GPSCoordinates))
        {
            pnlEntries.removeAllViews();
            for(EntryObjectDTO entry : lap.Day.getTrainingDay().objects)
            {
                ImageView img = new ImageView(getContext());
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                img.setImageDrawable(getContext().getResources().getDrawable(getImage(entry)));
                LinearLayout.LayoutParams params = new ActionMenuView.LayoutParams( Helper.toDp(32), Helper.toDp(32));
                params.setMargins(0,0,Helper.toDp(3),0);
                pnlEntries.addView(img,params);
            }
        }
        return row;
    }

    int getImage(EntryObjectDTO entry)
    {
        if (entry instanceof StrengthTrainingEntryDTO)
        {
            return R.drawable.strength_training_tile;
        }
        else if (entry instanceof SuplementsEntryDTO)
        {
            return R.drawable.supple_tile;
        }
        if (entry instanceof GPSTrackerEntryDTO)
        {
            return R.drawable.cardio_tile;
        }
        if (entry instanceof BlogEntryDTO)
        {
            return R.drawable.blog_tile;
        }
        if (entry instanceof SizeEntryDTO)
        {
            return R.drawable.sizes_tile;
        }

        return 0;
    }
}
