package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.EntryObjectColors;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDayInfo;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import com.splunk.mint.Mint;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;

import java.util.Calendar;
import java.util.HashMap;

public class CalendarAdapter extends BaseAdapter {
    static final int FIRST_DAY_OF_WEEK =Calendar.getInstance().getFirstDayOfWeek()-1; // Sunday = 0, Monday = 1


    private Context mContext;

    private java.util.Calendar month;
    private Calendar selectedDate;
    private HashMap<DateTime, TrainingDayInfo> items;

    public CalendarAdapter(Context c, Calendar monthCalendar) {
        month = monthCalendar;
        selectedDate = (Calendar)monthCalendar.clone();
        mContext = c;
        month.set(Calendar.DAY_OF_MONTH, 1);
        this.items = new HashMap<DateTime, TrainingDayInfo>();
        refreshDays();
    }

    public void setItems(HashMap<DateTime, TrainingDayInfo> days) {
        this.items = days;
    }


    public int getCount() {
        return days.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_item, null);

        }
        dayView = (TextView)v.findViewById(R.id.date);
        LinearLayout pnlMain= (LinearLayout) v.findViewById(R.id.pnlMain);
        // disable empty days from the beginning
        if(days[position].equals("")) {
            dayView.setClickable(false);
            dayView.setFocusable(false);
        }
//        else {
//            // mark current day as focused
//            if(month.get(Calendar.YEAR)== selectedDate.get(Calendar.YEAR) && month.get(Calendar.MONTH)== selectedDate.get(Calendar.MONTH) && days[position].equals(""+selectedDate.get(Calendar.DAY_OF_MONTH))) {
//                v.setBackgroundResource(R.color.calendar_item_bg_focused);
//            }
//            else {
//                v.setBackgroundResource(R.color.calendar_item_bg);
//            }
//        }
        dayView.setText(days[position]);

        // create date string for comparison
        String date = days[position];
        DateTime myDate=null;
        TrainingDayInfo info=null;

        if(date!="")
        {
            int dayNumer=Integer.parseInt(date);
            if(dayNumer<=month.getActualMaximum(Calendar.DAY_OF_MONTH))
            {
                myDate= new DateTime(month.get(Calendar.YEAR),month.get(Calendar.MONTH)+1,Integer.parseInt(date),0,0, ISOChronology.getInstanceUTC());
            }

        }

        LinearLayout entriesRects = (LinearLayout)v.findViewById(R.id.entriesRects);
        entriesRects.removeAllViews();

        if(myDate!=null && items.containsKey(myDate))
        {

            info=items.get(myDate);
            pnlMain.setBackgroundDrawable (mContext.getResources().getDrawable(R.drawable.calendar_item_with_entry));
            for (EntryObjectDTO entry:info.getTrainingDay().objects)
            {
                LinearLayout rect = new LinearLayout(parent.getContext());
                rect.setBackgroundColor(getEntryColor(entry));
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(Helper.toDp(8),Helper.toDp(8));
                param.setMargins(Helper.toDp(2),0,Helper.toDp(2),0);
                rect.setLayoutParams(param);
                entriesRects.addView(rect);
            }

        }
        else
        {
            pnlMain.setBackgroundDrawable (mContext.getResources().getDrawable(R.drawable.calendar_item_border));
        }

        // mark current day as focused
        if(month.get(Calendar.YEAR)== selectedDate.get(Calendar.YEAR) && month.get(Calendar.MONTH)== selectedDate.get(Calendar.MONTH) && days[position].equals(""+selectedDate.get(Calendar.DAY_OF_MONTH))) {
            pnlMain.setBackgroundDrawable (mContext.getResources().getDrawable(R.drawable.calendar_item_today));
        }

//        if(date.length()==1) {
//            date = "0"+date;
//        }
//        String monthStr = ""+(month.get(Calendar.MONTH)+1);
//        if(monthStr.length()==1) {
//            monthStr = "0"+monthStr;
//        }

        // show icon if date is not empty and it exists in the items array
        ImageView iw = (ImageView)v.findViewById(R.id.date_icon);
        if(info!=null && info.isModified() ) {
            iw.setVisibility(View.VISIBLE);
        }
        else {
            iw.setVisibility(View.INVISIBLE);
        }
        return v;
    }

    int getEntryColor(EntryObjectDTO entry)
    {
        if(entry instanceof StrengthTrainingEntryDTO)
        {
            return EntryObjectColors.StrengthTraining;
        }
        else if(entry instanceof SuplementsEntryDTO)
        {
            return EntryObjectColors.Supplements;
        }
        else if(entry instanceof A6WEntryDTO)
        {
            return EntryObjectColors.A6W;
        }
        else if(entry instanceof BlogEntryDTO)
        {
            return EntryObjectColors.Blog;
        }
        else if(entry instanceof GPSTrackerEntryDTO)
        {
            return EntryObjectColors.GPSTracker;
        }
        else if(entry instanceof SizeEntryDTO)
        {
            return EntryObjectColors.Measurements;
        }
        return -1;
    }

    public void refreshDays()
    {
        int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDay = (int)month.get(Calendar.DAY_OF_WEEK);

        // figure size of the array
        if(firstDay==1){
            days = new String[lastDay+(FIRST_DAY_OF_WEEK*6)];
        }
        else {
            days = new String[lastDay+firstDay-(FIRST_DAY_OF_WEEK+1)];
        }

        int j=FIRST_DAY_OF_WEEK;

        // populate empty days before first real day
        if(firstDay>1) {
            for(j=0;j<firstDay-FIRST_DAY_OF_WEEK;j++) {
                days[j] = "";
            }
        }
        else {
            for(j=0;j<FIRST_DAY_OF_WEEK*6;j++) {
                days[j] = "";
            }
            j=FIRST_DAY_OF_WEEK*6+1; // sunday => 1, monday => 7
        }

        if(j==0)
        {
            j=1;
            //in some strange situatin this j variable is 0 and the lower for loop throws excpetion. To detect this I send fake exception to bugsense with some important info. Maybe this will help
            IllegalArgumentException ex = new IllegalArgumentException();
            String str=String.format("%d %d %s",FIRST_DAY_OF_WEEK,firstDay,DateTime.now().toString());
            Mint.logException(str, str, ex);
        }
        // populate days
        int dayNumber = 1;
        for(int i=j-1;i<days.length;i++) {
            days[i] = ""+dayNumber;
            dayNumber++;
        }
    }

    // references to our items
    public String[] days;
}
