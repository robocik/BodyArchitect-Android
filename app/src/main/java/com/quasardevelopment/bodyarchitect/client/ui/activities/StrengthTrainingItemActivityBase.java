package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.mobeta.android.dslv.DragSortListView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.StrengthTrainingSetsAdapterBase;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAListView;
import com.quasardevelopment.bodyarchitect.client.ui.controls.TimerControl;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.UpgradeAccountFragment;
import com.quasardevelopment.bodyarchitect.client.util.ActivitiesSettings;
import com.quasardevelopment.bodyarchitect.client.util.AndroidHelper;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SerieDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.StrengthTrainingEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.StrengthTrainingItemDTO;

import java.util.List;


public abstract class StrengthTrainingItemActivityBase extends SimpleEntryObjectActivityBase
{
    StrengthTrainingItemDTO selectedItem;
    TextView tbExercise;
    TextView tbEmptySets;
    BAListView lstSets;
    MenuItem mnuAdd;
    MenuItem mnuAdvancedEdit;
    TimerControl ctrlTimer;
    StrengthTrainingSetsAdapterBase adapter;
    private MenuItem mnuMore;
    private ActionMode currentActionMode;

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

        fillSets();
        ctrlTimer.setIsStarted(getEditMode() && ActivitiesSettings.isGlobalTimerEnabled());
    }

    protected abstract StrengthTrainingSetsAdapterBase createAdapter();

    void fillSets()
    {
        Parcelable state = lstSets.onSaveInstanceState();

        adapter = createAdapter();
        lstSets.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetInvalidated();

        tbEmptySets.setVisibility(selectedItem.series.size()==0? View.VISIBLE:View.GONE);
        lstSets.setVisibility(selectedItem.series.size()>0?View.VISIBLE:View.GONE);

        lstSets.onRestoreInstanceState(state);
    }

    @Override
    protected void onPause() {
        ctrlTimer.setIsStarted(false);
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected String getEmptyMessage()
    {
        return getString(R.string.strength_training_item_activity_empty_sets);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        LocalObjectKey itemId=(LocalObjectKey)getIntent().getSerializableExtra("ItemId");
        StrengthTrainingEntryDTO entry=getEntry();

        selectedItem=Helper.getItem(itemId, entry.entries);


        getSupportActionBar().setSubtitle(R.string.strength_training_activity_title);

        View inflate = getLayoutInflater().inflate(R.layout.activity_strength_training_item, null);
        setMainContent(inflate);

        ctrlTimer= (TimerControl) findViewById(R.id.ctrlTimer);
        lstSets = (BAListView) findViewById(R.id.strenght_training_item_activity_lst_sets);
        tbExercise = (TextView) findViewById(R.id.strenght_training_item_exercise);
        tbEmptySets = (TextView) findViewById(R.id.strenght_training_activity_empty_sets);
        tbEmptySets.setText(getEmptyMessage());
        tbExercise.setText(selectedItem.exercise.getDisplayText());


        lstSets.setLongClickable(true);
        lstSets.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

        lstSets.setDropListener(new DragSortListView.DropListener() {
            @Override
            public void drop(int from, int to) {
                reorderItem(from, to);
            }
        });
        lstSets.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isAdvancedEditMode())
                {
                    currentActionMode.setTitle(String.format(getString(R.string.advanced_edit_selected_items),AndroidHelper.GetCheckedItemsCount((ListView) adapterView)));
                    return;
                }
                SerieDTO item= (SerieDTO) lstSets.getAdapter().getItem(i);
                Intent myIntent = new Intent(StrengthTrainingItemActivityBase.this, GetSetActivityType());


                //if (item.exercise != ExerciseDTO.Deleted)
                {
//                    if (item.strengthTrainingItem.exercise.exerciseType == WS_Enums.ExerciseType.Cardio)
//                    {
//
//                    }
//                    else
//                    {
//                        myIntent = new Intent(StrengthTrainingItemActivityBase.this, SetActivity.class);
//                    }

                    myIntent.putExtra("ItemId",new LocalObjectKey(item));
                    StrengthTrainingItemActivityBase.this.startActivity(myIntent);
                }
//                else
//                {
//                    BAMessageBox.ShowError(ApplicationStrings.StrengthWorkoutPage_ErrCannotViewDeletedExercise);
//                }
            }
        });

        //registerForContextMenu(lstSets);


    }

    protected abstract Class GetSetActivityType();

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        if(getEditMode())
        {
            mnuAdd=menu.add(Menu.NONE,1, Menu.NONE,R.string.button_add);
            mnuAdd.setIcon(getResources().getDrawable(R.drawable.content_new)) ;
//            mnuAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(mnuAdd,MenuItem.SHOW_AS_ACTION_ALWAYS);

            mnuAdvancedEdit=menu.add(Menu.NONE,2, Menu.NONE,R.string.button_advance_edit);
//            mnuAdvancedEdit.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            MenuItemCompat.setShowAsAction(mnuAdvancedEdit,MenuItem.SHOW_AS_ACTION_NEVER);
        }
        mnuMore=menu.add(Menu.NONE,3, Menu.NONE,R.string.button_more);
//        mnuMore.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        MenuItemCompat.setShowAsAction(mnuMore,MenuItem.SHOW_AS_ACTION_NEVER);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item==mnuAdd)
        {
            fillSets();
            Helper.SetSelection(lstSets, selectedItem.series.size() - 1);

            Intent myIntent = new Intent(StrengthTrainingItemActivityBase.this, GetSetActivityType());
            SerieDTO serie=addNewSet(selectedItem);
            myIntent.putExtra("ItemId",new LocalObjectKey(serie));
            if(Settings.getTreatSuperSetsAsOne())
            {
                List<StrengthTrainingItemDTO> joinedExercises = selectedItem.getJoinedItems();
                for (StrengthTrainingItemDTO strengthTrainingItemDto : joinedExercises)
                {
                    addNewSet(strengthTrainingItemDto);
                }
            }
            startActivity(myIntent);
        }
        if(item==mnuAdvancedEdit)
        {
            advancedEdit();
        }
        if(item==mnuMore)
        {
            Intent intent=new Intent(this,StrengthTrainingItemOptionsActivity.class);
            intent.putExtra("ItemId",new LocalObjectKey(selectedItem));
            startActivity(intent);
        }
        return true;
    }

    boolean isAdvancedEditMode()
    {
        return lstSets.isDragEnabled();
    }

    private void advancedEdit() {
        lstSets.setDragEnabled(!lstSets.isDragEnabled());
        if(isAdvancedEditMode())
        {
            lstSets.clearChoices();
            lstSets.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            currentActionMode=startSupportActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
                    MenuItem mnuItem = menu.add(android.view.Menu.NONE, 1, android.view.Menu.NONE, R.string.button_delete);
                    mnuItem.setIcon(getResources().getDrawable(R.drawable.content_discard)) ;
//                    mnuItem.setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);
                    MenuItemCompat.setShowAsAction(mnuItem,MenuItem.SHOW_AS_ACTION_ALWAYS);
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
                    return false;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                    advancedEdit();//leave advanced edit mode
                }
            }) ;
            currentActionMode.setTitle(String.format(getString(R.string.advanced_edit_selected_items), AndroidHelper.GetCheckedItemsCount(lstSets)));
        }
        else
        {
            lstSets.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        adapter.notifyDataSetChanged();
    }

    private void reorderItem(int from, int to) {
        if (!UpgradeAccountFragment.EnsureAccountType(this))
        {
            return;
        }
        SerieDTO item = adapter.getItem(from);
        selectedItem.series.remove(item);
        selectedItem.series.insertElementAt(item,to);
        adapter.remove(item);
        adapter.insert(item, to);
        lstSets.moveCheckState(from, to);
    }

    private void removeSelectedItems(ActionMode mode) {
        SparseBooleanArray checked =lstSets.getCheckedItemPositions();
        for (int i = lstSets.getCount()-1; i >=0; i--)
            if (checked.get(i)) {
                SerieDTO itemDto = adapter.getItem(i);
                selectedItem.series.remove(itemDto);
                adapter.remove(itemDto);
            }
        mode.finish();
        adapter.notifyDataSetChanged();
    }



    SerieDTO addNewSet(StrengthTrainingItemDTO item)
    {
        SerieDTO newSet = new SerieDTO();
        newSet.strengthTrainingItem=item;
        if(Settings.getCopyValuesFromNewSet() && item.series.size()>0)
        {
            SerieDTO previous=item.series.get(item.series.size()-1);
            newSet.repetitionNumber=previous.repetitionNumber;
            newSet.weight=previous.weight;
            newSet.setType=previous.setType;
            newSet.isSuperSlow=previous.isSuperSlow;
        }
        item.series.add(newSet);
        return newSet;
    }
}
