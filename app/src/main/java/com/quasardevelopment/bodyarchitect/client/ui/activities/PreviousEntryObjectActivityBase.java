package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.gesture.*;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDayInfo;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAListView;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.UpgradeAccountFragment;
import com.quasardevelopment.bodyarchitect.client.util.AndroidHelper;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.EntryObjectDTO;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 22.05.13
 * Time: 08:00
 * To change this template use File | Settings | File Templates.
 */
public abstract class PreviousEntryObjectActivityBase extends EntryObjectActivityBase implements GestureOverlayView.OnGesturePerformedListener {
    BAListView lstPrevious;
    TextView tbPreviousHeader;
    TabHost tabHost;
    private EntryObjectDTO oldEntry;
    android.view.MenuItem mnuPrevious;
    private GestureLibrary gLib;
    ActionMode currentActionMode;
    ImageButton btnPrevious;
    ImageButton btnNext;

    public void Connect(TabHost tabHost, BAListView lstPrevious, LinearLayout pnlPreviousHeader, GestureOverlayView gestureOverlayView)
    {
        Connect(tabHost);
        this.tabHost=tabHost;
        this.lstPrevious=lstPrevious;
        createPreviousHeader(pnlPreviousHeader);



        gLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gLib.load()) {
            finish();
        }

        gestureOverlayView.addOnGesturePerformedListener(this);
        gestureOverlayView.setGestureColor(Color.TRANSPARENT);
        gestureOverlayView.setUncertainGestureColor(Color.TRANSPARENT);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (PreviousEntryObjectActivityBase.this.tabHost.getCurrentTab() == 2) {
                    showOldTraining();
                }
                finishCurrentMode(true);
                supportInvalidateOptionsMenu();
            }
        });
        lstPrevious.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentActionMode != null && PreviousEntryObjectActivityBase.this.tabHost.getCurrentTab() == 2)
                {
                    currentActionMode.setTitle(String.format(getString(R.string.advanced_edit_selected_items), AndroidHelper.GetCheckedItemsCount((ListView) adapterView)));
                }
            }
        });
        lstPrevious.setLongClickable(true);
        lstPrevious.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentActionMode==null)
                {
                    ((ListView)adapterView).clearChoices();
                    previousEntryOperations();
                }
                return false;
            }
        });

        if(!ApplicationState.getCurrent().isPremium())
        {
            lstPrevious.setVisibility(View.GONE);
            TextView tbPremiumInfo = new TextView(this);
            tbPremiumInfo.setText(R.string.upgrade_premium_account_description);
            float textSize=getResources().getDimension(R.dimen.large_font);
            tbPremiumInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
            GestureOverlayView parent=(GestureOverlayView)lstPrevious.getParent();
            parent.addView(tbPremiumInfo);

        }
    }

    private void createPreviousHeader(LinearLayout pnlPreviousHeader) {
        btnPrevious = (ImageButton)getLayoutInflater().inflate(R.layout.image_button, null);
        btnPrevious.setImageResource(R.drawable.navigation_previous_item);
        btnNext = (ImageButton)getLayoutInflater().inflate(R.layout.image_button, null);
        btnNext.setImageResource(R.drawable.navigation_next_item);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 showNext();
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPrevious();
            }
        });
        this.tbPreviousHeader=new TextView(this);
        tbPreviousHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.large_font));

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.gravity= Gravity.CENTER_VERTICAL;
        pnlPreviousHeader.addView(btnPrevious,param);
        param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        param.weight=1;
        tbPreviousHeader.setGravity(Gravity.CENTER_HORIZONTAL |Gravity.CENTER_VERTICAL);
        tbPreviousHeader.setSingleLine(true);
        pnlPreviousHeader.addView(tbPreviousHeader,param);
        param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        param.gravity= Gravity.CENTER_VERTICAL;
        pnlPreviousHeader.addView(btnNext,param);


    }

    protected void showOldTraining()
    {
        if(ApplicationState.getCurrent()==null)
        {
            return;
        }
        if(oldEntry!=null)
        {
            ShowOldTraining(oldEntry);
        }

        final DateTime oldTrainingDate=ApplicationState.getCurrent().getTrainingDay().getTrainingDay().trainingDate.minusDays(6);

        List<TrainingDayInfo> oldDays= filter(new Predicate<TrainingDayInfo>() {
            public boolean apply(TrainingDayInfo item) {
                return item.getTrainingDay().trainingDate.isBefore(oldTrainingDate) || item.getTrainingDay().trainingDate.isEqual(oldTrainingDate);
            }
        }, ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().values());

        List<EntryObjectDTO> entries = Helper.getEntryObjects(oldDays);

        Collections.sort(entries,Collections.reverseOrder(new Comparator<EntryObjectDTO>() {
            @Override
            public int compare(EntryObjectDTO entryObjectDTO, EntryObjectDTO entryObjectDTO2) {
                return entryObjectDTO.trainingDay.trainingDate.compareTo(entryObjectDTO2.trainingDay.trainingDate);
            }
        }));
        oldEntry=Helper.FirstOrDefault(filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return item.getClass().equals(getEntryType()) && !item.isSame(getEntry());
            }
        }, entries));


        if(oldEntry!=null)
        {
            displayOldEntry(oldEntry);
        }
        else
        {
            showPrevious();
        }
    }

    protected abstract void ShowOldTraining(EntryObjectDTO oldEntry);

    private void displayOldEntry(EntryObjectDTO newEntry)
    {
        oldEntry = newEntry;
        DateTimeFormatter formatter = DateTimeFormat.longDate();
        tbPreviousHeader.setText( newEntry.trainingDay.trainingDate.toString(formatter));
        tbPreviousHeader.setVisibility( View.VISIBLE);
        ShowOldTraining(newEntry);


    }

    protected void showPrevious()
    {
        List<EntryObjectDTO> entries = Helper.getEntryObjects(ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().values());


        Collections.sort(entries,new Comparator<EntryObjectDTO>() {
            @Override
            public int compare(EntryObjectDTO entryObjectDTO, EntryObjectDTO entryObjectDTO2) {
                return entryObjectDTO.trainingDay.trainingDate.compareTo(entryObjectDTO2.trainingDay.trainingDate);
            }
        });

        entries=filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return item.getClass().equals(getEntryType()) && !item.isSame(getEntry());
            }
        }, entries);

        int position = 1;
        if (oldEntry != null)
        {
            EntryObjectDTO currentItem=Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
                public boolean apply(EntryObjectDTO item) {
                    return item.isSame(oldEntry);
                }
            }, entries));
            position = entries.indexOf(currentItem);
        }

        if (entries.size()==0 || position == 0)
        {
            BAMessageBox.ShowToast(R.string.previous_entry_activity_no_previous_entries);
        }
        else
        {
            EntryObjectDTO newEntry = entries.get(position - 1);


            displayOldEntry(newEntry);
        }
    }
    protected void showNext()
    {
        List<EntryObjectDTO> entries = Helper.getEntryObjects(ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().values());

        Collections.sort(entries,new Comparator<EntryObjectDTO>() {
            @Override
            public int compare(EntryObjectDTO entryObjectDTO, EntryObjectDTO entryObjectDTO2) {
                return entryObjectDTO.trainingDay.trainingDate.compareTo(entryObjectDTO2.trainingDay.trainingDate);
            }
        });

        //List<EntryObjectDTO> entries =sort(flatten(extract(ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().values(), on(TrainingDayInfo.class).getTrainingDay().objects)), on(EntryObjectDTO.class).trainingDay.trainingDate,DESCENDING );
        entries=filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return item.getClass().equals(getEntryType()) && !item.isSame(getEntry());
            }
        }, entries);

        int position = -1;
        if (oldEntry != null)
        {
            EntryObjectDTO currentItem=Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
                public boolean apply(EntryObjectDTO item) {
                    return item.isSame(oldEntry);
                }
            }, entries));
            position = entries.indexOf(currentItem);
        }

        if (entries.size()==0 || position == entries.size() - 1)
        {
            BAMessageBox.ShowToast(R.string.previous_entry_activity_no_previous_entries);
        }
        else
        {
            EntryObjectDTO newEntry = entries.get(position + 1);


            displayOldEntry(newEntry);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        if(tabHost.getCurrentTab()==2 && oldEntry!=null && ApplicationState.getCurrent().isPremium())
        {
            mnuPrevious=menu.add(Menu.NONE,1, Menu.NONE,R.string.button_select);
            mnuPrevious.setIcon(getResources().getDrawable(R.drawable.appbar_select)) ;
//            mnuPrevious.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(mnuPrevious, MenuItem.SHOW_AS_ACTION_ALWAYS);
        }


        return true;
    }

    boolean isPreviousSelectMode()
    {
        return lstPrevious.getChoiceMode()== AbsListView.CHOICE_MODE_MULTIPLE;
    }

    protected abstract void copyAllImplementation(EntryObjectDTO oldEntry);

    protected boolean finishCurrentMode(boolean invokeFinish)
    {
        if(currentActionMode!=null)
        {
            if(invokeFinish)
            {
                currentActionMode.finish();
            }
            currentActionMode=null;
            return true;
        }
        return false;
    }
    protected void startMode(ActionMode.Callback callback)
    {
        finishCurrentMode(true);
        currentActionMode=startSupportActionMode(callback);
    }
    void previousEntryOperations()
    {
        if(isPreviousSelectMode())
        {
            lstPrevious.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        }
        else
        {
            lstPrevious.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            startMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
                    android.view.MenuItem menuItem = menu.add(Menu.NONE, 1, Menu.NONE, android.R.string.copy);
                    menuItem.setIcon(R.drawable.content_copy);
                    menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, android.view.Menu menu) {

                    mode.setTitle(String.format(getString(R.string.advanced_edit_selected_items),0));
                    return false;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, android.view.MenuItem item) {
                    if(item.getItemId()==1)
                    {
                        if (!UpgradeAccountFragment.EnsureAccountType(PreviousEntryObjectActivityBase.this))
                        {
                            return true;
                        }


                        if (oldEntry!=null )
                        {
                            copyAllImplementation(oldEntry);
                            tabHost.setCurrentTab(0);
                            finishCurrentMode(true);
                        }
                        return true;
                    }

                    return false;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                    finishCurrentMode(false);
                    previousEntryOperations();

                }
            })   ;


        }
        BaseAdapter adapter=(BaseAdapter)lstPrevious.getAdapter();
        if(adapter!=null)
        {
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item==mnuPrevious)
        {
            previousEntryOperations();
            lstPrevious.clearChoices();
            //by default select all items
            for(int i=0; i < lstPrevious.getAdapter().getCount(); i++){
                lstPrevious.setItemChecked(i, true);
            }

            currentActionMode.setTitle(String.format(getString(R.string.advanced_edit_selected_items),AndroidHelper.GetCheckedItemsCount(lstPrevious)));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
        ArrayList<Prediction> predictions = gLib.recognize(gesture);
        for (Prediction prediction : predictions) {
            if (prediction.score > 1.0) {
                if(prediction.name.equals("swipeLeft"))
                {
                    onLeftSwipe();
                }
                else if(prediction.name.equals("swipeRight"))
                {
                    onRightSwipe();
                }
            }
        }
    }

    private void onRightSwipe() {

        showPrevious();
    }

    private void onLeftSwipe()
    {
        showNext();
    }
}
