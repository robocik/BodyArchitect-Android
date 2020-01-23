package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.view.ActionMode;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;

import ch.lambdaj.function.matcher.Predicate;
import com.mobeta.android.dslv.DragSortListView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.PreviousStrengthTrainingEntryAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.StrengthTrainingExercisesAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAListView;
import com.quasardevelopment.bodyarchitect.client.ui.controls.MoodButton;
import com.quasardevelopment.bodyarchitect.client.ui.controls.MoodChangedListener;
import com.quasardevelopment.bodyarchitect.client.ui.controls.TimerControl;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.UpgradeAccountFragment;
import com.quasardevelopment.bodyarchitect.client.util.*;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 24.04.13
 * Time: 07:39
 * To change this template use File | Settings | File Templates.
 */
public class StrengthTrainingActivity extends PreviousEntryObjectActivityBase
{
    TabHost tabHost;
    BAListView lstExercises;
    TextView tbExerciseHeader;
    Button btnStartTime;
    Button btnEndTime;
    Spinner cmbIntensity;
    MoodButton btnMood;
    android.support.v7.widget.SwitchCompat tsEntryStatus;
    TextView tbEmptyExercises;
    Button btnNowTime;
    android.view.MenuItem mnuAdd;
    android.view.MenuItem mnuTimer;
    android.view.MenuItem mnuAdvancedEdit;
    private android.view.MenuItem mnuMoreOptions;

    TimerControl ctrlTimer;
    StrengthTrainingExercisesAdapter adapter;


    BAListView lstPrevious;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ActivitiesSettings.clearStrengthTrainingPrefs();

        View inflate = getLayoutInflater().inflate(R.layout.activity_strength_training, null);
        setMainContent(inflate);

        tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();


        TabHost.TabSpec spec1=tabHost.newTabSpec(getString(R.string.strength_training_activity_header_exercises));
        spec1.setContent(R.id.strenght_training_activity_tab_exercises);
        spec1.setIndicator(getString(R.string.strength_training_activity_header_exercises));

        getSupportActionBar().setSubtitle(R.string.strength_training_activity_title);

        TabHost.TabSpec spec2=tabHost.newTabSpec(getString(R.string.strength_training_activity_header_info));
        spec2.setIndicator(getString(R.string.strength_training_activity_header_info));
        spec2.setContent(R.id.strenght_training_activity_tab_info);


        TabHost.TabSpec spec3=tabHost.newTabSpec(getString(R.string.strength_training_activity_header_preview));
        spec3.setContent(R.id.strenght_training_activity_tab_preview);
        spec3.setIndicator(getString(R.string.strength_training_activity_header_preview));
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);

        createIntensity();

        lstPrevious= (BAListView) findViewById(R.id.preview_activity_list);
        LinearLayout pnlPreviousHeader= (LinearLayout) findViewById(R.id.pnlPreviousHeader);

        ctrlTimer= (TimerControl) findViewById(R.id.ctrlTimer);
        lstExercises = (BAListView)findViewById(R.id.strenght_training_activity_lst_exercises);
        tbEmptyExercises = (TextView)findViewById(R.id.strenght_training_activity_empty_exercises);
        tbExerciseHeader = (TextView)findViewById(R.id.entry_activity_header_date);
        tsEntryStatus = (android.support.v7.widget.SwitchCompat)findViewById(R.id.entry_activity_entry_status);

        tsEntryStatus.setTextOn(getString(R.string.status_done));
        tsEntryStatus.setTextOff(getString(R.string.status_planned));
        tsEntryStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                getEntry().status=checked? WS_Enums.EntryObjectStatus.Done: WS_Enums.EntryObjectStatus.Planned;
            }
        });

        lstExercises.setDropListener(new DragSortListView.DropListener() {
            @Override
            public void drop(int from, int to) {
                reorderItem(from, to);
            }
        });

        btnMood = (MoodButton)findViewById(R.id.strenght_training_activity_mood);
        btnMood.addListener(new MoodChangedListener() {
            @Override
            public void moodChanged(WS_Enums.Mood mood) {
                getEntry().mood = mood;
            }
        });
        createTrainingTime();


        lstExercises.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isAdvancedEditMode())
                {
                    currentActionMode.setTitle(String.format(getString(R.string.advanced_edit_selected_items),AndroidHelper.GetCheckedItemsCount(lstExercises)));
                    return;
                }
                StrengthTrainingItemDTO item= (StrengthTrainingItemDTO) lstExercises.getAdapter().getItem(i);
                Intent myIntent ;


                //if (item.exercise != ExerciseDTO.Deleted)
                {
                    if (item.exercise.exerciseType == WS_Enums.ExerciseType.Cardio)
                    {
                        myIntent = new Intent(StrengthTrainingActivity.this, CardioTrainingItemActivity.class);

//                        CardioTrainingItemActivity.selectedStrengthTrainingItem=item;
                    }
                    else
                    {
                        myIntent = new Intent(StrengthTrainingActivity.this, StrengthWorkoutItemActivity.class);
//                        StrengthWorkoutItemActivity.selectedStrengthTrainingItem=item;
                    }
                    myIntent.putExtra("ItemId",new LocalObjectKey(item));
                    StrengthTrainingActivity.this.startActivity(myIntent);
                }
            }
        });

        lstExercises.setLongClickable(true);
        lstExercises.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentActionMode==null)
                {
                    ((ListView)adapterView).clearChoices();
                    advancedEdit();
                }
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        GestureOverlayView gestureView= (GestureOverlayView) findViewById(R.id.previous_gesture_view);
        Connect(tabHost,lstPrevious,pnlPreviousHeader,gestureView);
        setReadOnly();
    }

    boolean isAdvancedEditMode()
    {
        return lstExercises.isDragEnabled();
    }

    private void reorderItem(int from, int to) {
        if (!UpgradeAccountFragment.EnsureAccountType(this))
        {
            return;
        }
        StrengthTrainingItemDTO item = adapter.getItem(from);
        StrengthTrainingEntryDTO entry=getEntry();
        entry.entries.remove(item);
        entry.entries.insertElementAt(item,to);
        adapter.remove(item);
        adapter.insert(item, to);
        lstExercises.moveCheckState(from, to);
        getEntry().ResetPositions();
    }

    void setReadOnly()
    {
        boolean readOnly=!getEditMode();
        //tsEntryStatus.setEnabled(!readOnly);
        tsEntryStatus.setClickable(!readOnly);
        btnMood.setEnabled(!readOnly);
        btnEndTime.setEnabled(!readOnly);
        btnStartTime.setEnabled(!readOnly);
        btnNowTime.setVisibility(readOnly ? View.GONE : View.VISIBLE);
        cmbIntensity.setEnabled(!readOnly);
    }

    private void createTrainingTime() {
        btnNowTime = (Button)findViewById(R.id.strength_training_btn_now_time);
        btnNowTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                getEntry().endTime = DateTimeHelper.removeTimeZone(DateTime.now());
                btnEndTime.setText(getEntry().endTime.toString(DateTimeFormat.shortTime()));
            }
        });

        btnStartTime = (Button)findViewById(R.id.strength_training_btn_start_time);
        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (getEntry().startTime == null) {
                    getEntry().startTime = DateTimeHelper.removeTimeZone(DateTime.now());
                }

                new TimePickerDialog(StrengthTrainingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT")) ;
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        getEntry().startTime = DateTimeHelper.removeTimeZone(new DateTime(c));
                        Button btn = (Button) view;
                        btn.setText(getEntry().startTime.toString(DateTimeFormat.shortTime()));
                    }
                }, getEntry().startTime.getHourOfDay(), getEntry().startTime.getMinuteOfHour(), true).show();
            }
        });
        btnEndTime = (Button)findViewById(R.id.strength_training_btn_end_time);
        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (getEntry().endTime == null) {
                    getEntry().endTime = DateTimeHelper.removeTimeZone(DateTime.now());
                }

                new TimePickerDialog(StrengthTrainingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT")) ;
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        getEntry().endTime = DateTimeHelper.removeTimeZone(new DateTime(c));
                        Button btn = (Button) view;
                        btn.setText(getEntry().endTime.toString(DateTimeFormat.shortTime()));
                    }
                }, getEntry().endTime.getHourOfDay(), getEntry().endTime.getMinuteOfHour(), true).show();
            }
        });
    }

    private void createIntensity() {
        cmbIntensity = (Spinner)findViewById(R.id.strenght_training_activity_combobox_intensity);
        final String array_spinner[]=new String[4];
        array_spinner[0]= EnumLocalizer.Translate(WS_Enums.Intensity.NotSet);
        array_spinner[1]= EnumLocalizer.Translate(WS_Enums.Intensity.Low);
        array_spinner[2]= EnumLocalizer.Translate(WS_Enums.Intensity.Medium);
        array_spinner[3]= EnumLocalizer.Translate(WS_Enums.Intensity.Hight);
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbIntensity.setAdapter(adapter);
        cmbIntensity.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getEntry().intensity = WS_Enums.Intensity.values()[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
    }



    public StrengthTrainingEntryDTO getEntry()
    {
        return  super.getEntry();
    }

    void fillExercises()
    {
        Parcelable state = lstExercises.onSaveInstanceState();
        VectorStrengthTrainingItemDTO entries=getEntry().entries;
        adapter = new StrengthTrainingExercisesAdapter(this,R.layout.strength_training_exercise_row, entries);
        lstExercises.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lstExercises.onRestoreInstanceState(state);
        tbEmptyExercises.setVisibility(entries.size() == 0 ? View.VISIBLE : View.GONE);
        lstExercises.setVisibility(entries.size() > 0 ? View.VISIBLE : View.GONE);
    }
    @Override
    protected void show(boolean reload)
    {

        fillExercises();


        DateTimeFormatter formatter =DateTimeFormat.longDate();
        if(getEntry().startTime!=null)
        {
            btnStartTime.setText(getEntry().startTime.toString(DateTimeFormat.shortTime()));
        }
        if(getEntry().endTime!=null)
        {
            btnEndTime.setText(getEntry().endTime.toString(DateTimeFormat.shortTime()));
        }

        cmbIntensity.setSelection(getEntry().intensity.getCode());
        btnMood.setMood(getEntry().mood);
        tsEntryStatus.setChecked(getEntry().status == WS_Enums.EntryObjectStatus.Done);
        tbExerciseHeader.setText(getEntry().trainingDay.trainingDate.toString(formatter));

    }

    @Override
    protected void ShowOldTraining(EntryObjectDTO oldEntry) {
        StrengthTrainingEntryDTO strength = (StrengthTrainingEntryDTO) oldEntry;
        PreviousStrengthTrainingEntryAdapter previousAdapter = new PreviousStrengthTrainingEntryAdapter(this,R.layout.previous_strength_training_item_row,strength.entries);
        lstPrevious.setAdapter(previousAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        if(tabHost.getCurrentTab()!=2)
        {

            if(getEditMode())
            {
                mnuTimer=menu.add(Menu.NONE,2,Menu.NONE,ctrlTimer.isStarted()?R.string.strength_training_activity_stop_timer:R.string.strength_training_activity_start_timer);
//                mnuTimer.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                MenuItemCompat.setShowAsAction(mnuTimer,MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

                if(tabHost.getCurrentTab()==0)
                {
                    mnuAdd=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_add);
                    mnuAdd.setIcon(getResources().getDrawable(R.drawable.content_new)) ;
//                    mnuAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    MenuItemCompat.setShowAsAction(mnuAdd,MenuItem.SHOW_AS_ACTION_ALWAYS);

                    mnuAdvancedEdit=menu.add(Menu.NONE,3,Menu.NONE,R.string.button_advance_edit);
//                    mnuAdvancedEdit.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                    MenuItemCompat.setShowAsAction(mnuAdvancedEdit,MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                }
            }
        }

        mnuMoreOptions=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_more);
//        mnuMoreOptions.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        MenuItemCompat.setShowAsAction(mnuMoreOptions,MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        return true;
    }

    @Override
    protected void ReturnBack() {
        ActivitiesSettings.setGlobalTimerEnabled(false);
        ApplicationState.getCurrent().timerStartTime=null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ctrlTimer.setIsStarted(getEditMode() &&  ActivitiesSettings.isGlobalTimerEnabled());

        if (!ctrlTimer.isStarted() && ApplicationState.getCurrent().isPremium() && Settings.getStartTimer() && getEntry().isNew())
        {
            startTimer(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ctrlTimer.setIsStarted(false);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item==mnuAdd)
        {
            Intent myIntent = new Intent(this, ExerciseSelectorActivity.class);
            startActivityForResult(myIntent,1);
        }
        if(item==mnuTimer)
        {
            if (!UpgradeAccountFragment.EnsureAccountType(this))
            {
                return true;
            }
            startTimer(!ActivitiesSettings.isGlobalTimerEnabled());
            invalidateOptionsMenu();
        }
        if(item==mnuAdvancedEdit)
        {
            advancedEdit();
        }
        if(item==mnuMoreOptions)
        {
            Intent intent = new Intent(this,StrengthTrainingOptionsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    void advancedEdit()
    {
        lstExercises.setDragEnabled(!lstExercises.isDragEnabled());
        if(isAdvancedEditMode())
        {
            lstExercises.clearChoices();
            lstExercises.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            currentActionMode=startSupportActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
                    mnuDelete=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_delete);
                    mnuDelete.setIcon(R.drawable.content_discard) ;
//                    mnuDelete.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    MenuItemCompat.setShowAsAction(mnuDelete,MenuItem.SHOW_AS_ACTION_ALWAYS);


                    mnuDelete=menu.add(Menu.NONE,2,Menu.NONE,R.string.button_join_superset);
                    mnuDelete.setIcon(R.drawable.content_join) ;
//                    mnuDelete.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    MenuItemCompat.setShowAsAction(mnuDelete,MenuItem.SHOW_AS_ACTION_ALWAYS);

                    mnuDelete=menu.add(Menu.NONE,3,Menu.NONE,R.string.button_split_superset);
                    mnuDelete.setIcon(R.drawable.content_split) ;
//                    mnuDelete.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    MenuItemCompat.setShowAsAction(mnuDelete,MenuItem.SHOW_AS_ACTION_ALWAYS);
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
                        removeSelectedItems(mode);
                        return true;
                    }
                    if(item.getItemId()==2)
                    {
                        superSetOperation(mode,true);
                        return true;
                    }
                    if(item.getItemId()==3)
                    {
                        superSetOperation(mode,false);
                        return true;
                    }
                    return false;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                    advancedEdit();//leave advanced edit mode
                    currentActionMode=null;
                }
            })   ;

            currentActionMode.setTitle(String.format(getString(R.string.advanced_edit_selected_items),AndroidHelper.GetCheckedItemsCount(lstExercises)));
        }
        else
        {
            lstExercises.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }



        adapter.notifyDataSetChanged();
    }

    private void superSetOperation(ActionMode mode, boolean join) {
        StrengthTrainingEntryDTO entry=getEntry();
        SparseBooleanArray checked =lstExercises.getCheckedItemPositions();
        String superSetGroup=UUID.randomUUID().toString();
        for (int i = lstExercises.getCount()-1; i >=0; i--)
            if (checked.get(i)) {
                StrengthTrainingItemDTO itemDto = adapter.getItem(i);
                itemDto.superSetGroup=join?superSetGroup:null;
            }

        checkSuperSets(entry);
        mode.finish();
        adapter.notifyDataSetChanged();
    }

    void checkSuperSets(StrengthTrainingEntryDTO entry)
    {
        List<StrengthTrainingItemDTO> entries= filter(new Predicate<StrengthTrainingItemDTO>() {
            public boolean apply(StrengthTrainingItemDTO item) {
                return !TextUtils.isEmpty(item.superSetGroup);
            }
        }, getEntry().entries);

        for (final StrengthTrainingItemDTO item : entries)
        {
            List<StrengthTrainingItemDTO> forSingleGroup= filter(new Predicate<StrengthTrainingItemDTO>() {
                public boolean apply(StrengthTrainingItemDTO item) {
                    return item.superSetGroup.equals(item.superSetGroup);
                }
            }, entries);

            if (forSingleGroup.size() < 2)
            {
                item.superSetGroup = null;
            }
        }
    }

    private void removeSelectedItems(ActionMode mode) {
        StrengthTrainingEntryDTO entry=getEntry();
        SparseBooleanArray checked =lstExercises.getCheckedItemPositions();
        for (int i = lstExercises.getCount()-1; i >=0; i--)
            if (checked.get(i)) {
                StrengthTrainingItemDTO itemDto = adapter.getItem(i);
                entry.entries.remove(itemDto);
                adapter.remove(itemDto);
            }
        mode.finish();
        adapter.notifyDataSetChanged();

        tbEmptyExercises.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
        lstExercises.setVisibility(adapter.getCount() > 0 ? View.VISIBLE : View.GONE);
    }

    private void startTimer(boolean start)
    {
        ActivitiesSettings.setGlobalTimerEnabled( start);
        //this block is for crash restore mechanism
        if(ApplicationState.getCurrent().timerStartTime==null)
        {
            ApplicationState.getCurrent().timerStartTime = DateTime.now();
        }
        //ApplicationState.getCurrent().timerStartTime = DateTime.now();
        ctrlTimer.setIsStarted(ActivitiesSettings.isGlobalTimerEnabled());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                UUID exerciseId=(UUID)data.getSerializableExtra("ItemId");
                ExerciseDTO exercise=ObjectsReposidory.getCache().getExercises().GetItem(exerciseId);
                addExercise(exercise);
            }
        }
    }//onActivityResult


    void addExercise(ExerciseLightDTO exercise)
    {
        StrengthTrainingEntryDTO entry=getEntry();
        StrengthTrainingItemDTO item = new StrengthTrainingItemDTO();
        item.exercise=exercise;
        entry.entries.add(item);
        item.strengthTrainingEntry=entry;
        item.position=entry.entries.size();
        fillExercises();
        //todo:what's this?
        lstExercises.getTop();


        Helper.SetSelection(lstExercises,entry.entries.size()-1);
    }

    @Override
    protected void copyAllImplementation(EntryObjectDTO oldEntry){
        if (oldEntry != null)
        {
            SparseBooleanArray checked =lstPrevious.getCheckedItemPositions();
            StrengthTrainingEntryDTO strength=getEntry();
            StrengthTrainingEntryDTO oldStrength = (StrengthTrainingEntryDTO)oldEntry;
            copyStrengthTrainingProperties(strength, oldStrength);

            TrainingBuilder builder = new TrainingBuilder();
            BaseAdapter previousAdapter = (BaseAdapter) lstPrevious.getAdapter();
            for (int i = 0; i <lstPrevious.getCount(); i++)
                if (checked.get(i)) {
                    StrengthTrainingItemDTO itemDto = (StrengthTrainingItemDTO) previousAdapter.getItem(i);
                    StrengthTrainingItemDTO newItem=Helper.Copy(itemDto,true);
                    builder.PrepareCopiedStrengthTraining(newItem,Settings.getCopyStrengthTrainingMode());
                    if(!Settings.getCopyStrengthTrainingMode().equals(Settings.CopyStrengthTrainingMode.OnlyExercises))
                    {
                        builder.SetPreviewSets(itemDto,newItem);
                    }
                    strength.entries.add(newItem);
                    newItem.strengthTrainingEntry=strength;
                }

            builder.CleanSingleSupersets(strength);
            strength.ResetPositions();
            show(true);
        }
    }

    private void copyStrengthTrainingProperties(StrengthTrainingEntryDTO strength, StrengthTrainingEntryDTO oldStrength)
    {
        if (strength.intensity.equals(WS_Enums.Intensity.NotSet))
        {//if intensity is not set then we assume that user didn't set this property so we can copy all other values from previous day
            strength.intensity = oldStrength.intensity;
            strength.reportStatus = oldStrength.reportStatus;
            //strength.MyPlace = oldStrength.MyPlace;//uncommnt this or not:)
            strength.trainingPlanId = oldStrength.trainingPlanId;
            strength.trainingPlanItemId = oldStrength.trainingPlanItemId;
        }
    }

    @Override
    protected void BeforeSaving() {
        StrengthTrainingEntryDTO entry=getEntry();
        if(entry!=null && entry.endTime==null)
        {
            entry.endTime=DateTimeHelper.removeTimeZone(DateTime.now());
        }
    }

    @Override
    protected void ValidateBeforeSave(final Functions.IAction1<Boolean> validationResult) {
        List<SerieDTO> incorrectSets = SerieValidator.GetIncorrectSets(getEntry());

        if (incorrectSets.size() > 0)
        {
            StringBuilder builder = new StringBuilder();

            builder.append(getString(R.string.strength_training_activity_incorrect_sets) + "\n");


            for (Map.Entry<ExerciseLightDTO, List<SerieDTO>> incorrectSet : groupIncorrectSetsByExercise(incorrectSets).entrySet() )
            {
                builder.append(incorrectSet.getKey().getDisplayText() + ": ");
                for (SerieDTO serieDto : incorrectSet.getValue())
                {
                    builder.append(Helper.GetDisplayText(serieDto) + ", ");
                }
                builder.append("\n");
            }

            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setTitle(R.string.html_app_name);
            dlgAlert.setMessage(builder.toString());
            dlgAlert.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    validationResult.Action(true);
                }
            });
            dlgAlert.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    validationResult.Action(false);
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return;
        }
        validationResult.Action(true);
    }

    Map<ExerciseLightDTO, List<SerieDTO>> groupIncorrectSetsByExercise(List<SerieDTO> incorrectSets)
    {
        Map<ExerciseLightDTO, List<SerieDTO>> groups = new HashMap<ExerciseLightDTO, List<SerieDTO>>();
        for (SerieDTO set : incorrectSets) {
            ExerciseLightDTO exercise = set.strengthTrainingItem.exercise;
            List<SerieDTO> group = groups.get(exercise);
            if (group == null) {
                group = new ArrayList<SerieDTO>();
                groups.put(exercise, group);
            }
            group.add(set);
        }
        return groups;
    }
}
