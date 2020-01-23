package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.mobeta.android.dslv.DragSortListView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.SupplementItemsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAListView;
import com.quasardevelopment.bodyarchitect.client.util.AndroidHelper;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 26.05.13
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class SupplementsActivity extends PreviousEntryObjectActivityBase {
    TabHost tabHost;
    EditText txtComment;

    DragSortListView lstSupplements;
    TextView tbSupplementHeader;
    android.support.v7.widget.SwitchCompat tsEntryStatus;
    TextView tbEmptySupplements;
    android.view.MenuItem mnuAdd;
    android.view.MenuItem mnuAdvancedEdit;
    BAListView lstPrevious;
    private SwitchCompat tsReportStatus;
    SupplementItemsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        View inflate = getLayoutInflater().inflate(R.layout.activity_supplements, null);
        setMainContent(inflate);

        tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();


        getSupportActionBar().setSubtitle(R.string.supplements_activity_title);

        TabHost.TabSpec spec1=tabHost.newTabSpec(getString(R.string.supplements_activity_header_supplements));
        spec1.setContent(R.id.supplements_activity_tab_supplements);
        spec1.setIndicator(getString(R.string.supplements_activity_header_supplements));


        TabHost.TabSpec spec2=tabHost.newTabSpec(getString(R.string.supplements_activity_header_info));
        spec2.setIndicator(getString(R.string.strength_training_activity_header_info));
        spec2.setContent(R.id.supplements_activity_tab_info);


        TabHost.TabSpec spec3=tabHost.newTabSpec(getString(R.string.strength_training_activity_header_preview));
        spec3.setContent(R.id.supplements_activity_tab_preview);
        spec3.setIndicator(getString(R.string.strength_training_activity_header_preview));
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);

        LinearLayout pnlPreviousHeader= (LinearLayout) findViewById(R.id.pnlPreviousHeader);
        lstPrevious= (BAListView) findViewById(R.id.preview_activity_list);

        lstSupplements = (DragSortListView)findViewById(R.id.supplements_activity_lst_supplements);
        tbEmptySupplements = (TextView)findViewById(R.id.supplements_activity_empty_supplements);
        tbSupplementHeader = (TextView)findViewById(R.id.entry_activity_header_date);
        tsEntryStatus = (android.support.v7.widget.SwitchCompat)findViewById(R.id.entry_activity_entry_status);
        tsReportStatus = (android.support.v7.widget.SwitchCompat)findViewById(R.id.entry_activity_report_status);
        txtComment = (EditText)findViewById(R.id.txt_comment);
        txtComment.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String comment=s.toString();
                getEntry().comment= !TextUtils.isEmpty(comment)?comment:null;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        tsEntryStatus.setTextOn(getString(R.string.status_done));
        tsEntryStatus.setTextOff(getString(R.string.status_planned));
        tsEntryStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                getEntry().status=checked? WS_Enums.EntryObjectStatus.Done: WS_Enums.EntryObjectStatus.Planned;
            }
        });

        tsReportStatus.setTextOn(getString(R.string.entry_object_activity_show_in_reports_show));
        tsReportStatus.setTextOff(getString(R.string.entry_object_activity_show_in_reports_hide));
        tsReportStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                getEntry().reportStatus=checked? WS_Enums.ReportStatus.ShowInReport: WS_Enums.ReportStatus.SkipInReport;
            }
        });


        lstSupplements.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isAdvancedEditMode())
                {
                    currentActionMode.setTitle(String.format(getString(R.string.advanced_edit_selected_items),AndroidHelper.GetCheckedItemsCount((ListView) adapterView)));
                    return;
                }
                SuplementItemDTO item= (SuplementItemDTO) lstSupplements.getAdapter().getItem(i);
                Intent myIntent = new Intent(SupplementsActivity.this, SupplementItemActivity.class);

                myIntent.putExtra("ItemId",new LocalObjectKey(item));
                SupplementsActivity.this.startActivity(myIntent);
            }
        });
        lstSupplements.setLongClickable(true);
        lstSupplements.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

    public SuplementsEntryDTO getEntry()
    {
        return  super.getEntry();
    }

    void fillSupplements()
    {
        Parcelable state = lstSupplements.onSaveInstanceState();
        VectorSuplementItemDTO entries=getEntry().items;
        adapter = new SupplementItemsAdapter(this,R.layout.supplement_item_row, entries);
        lstSupplements.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lstSupplements.onRestoreInstanceState(state);
        tbEmptySupplements.setVisibility(entries.size() == 0 ? View.VISIBLE : View.GONE);
        lstSupplements.setVisibility(entries.size() > 0 ? View.VISIBLE : View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        if(tabHost.getCurrentTab()!=2)
        {

            if(getEditMode())
            {
                if(tabHost.getCurrentTab()==0)
                {
                    mnuAdd=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_add);
                    mnuAdd.setIcon(getResources().getDrawable(R.drawable.content_new)) ;
//                    mnuAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    MenuItemCompat.setShowAsAction(mnuAdd, MenuItem.SHOW_AS_ACTION_ALWAYS);

                    mnuAdvancedEdit=menu.add(Menu.NONE,3,Menu.NONE,R.string.button_advance_edit);
//                    mnuAdvancedEdit.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                    MenuItemCompat.setShowAsAction(mnuAdvancedEdit,MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                }




            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item==mnuAdd)
        {
            Intent myIntent = new Intent(this, SupplementSelectorActivity.class);
            startActivityForResult(myIntent, 1);
        }
        if(item==mnuAdvancedEdit)
        {
            advancedEdit();
        }
        return true;
    }

    private void setReadOnly() {
        boolean isReadOnly=!getEditMode();
        tsReportStatus.setClickable(!isReadOnly);
        tsEntryStatus.setClickable(!isReadOnly);
        txtComment.setEnabled(!isReadOnly);
    }


    @Override
    protected void ShowOldTraining(EntryObjectDTO oldEntry) {
        VectorSuplementItemDTO entries=((SuplementsEntryDTO)oldEntry).items;
        adapter = new SupplementItemsAdapter(this,R.layout.supplement_item_row, entries);
        lstPrevious.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void copyAllImplementation(EntryObjectDTO oldEntry) {
        if (oldEntry != null)
        {
            SparseBooleanArray checked =lstPrevious.getCheckedItemPositions();
            BaseAdapter previousAdapter = (BaseAdapter) lstPrevious.getAdapter();

            for (int i = 0; i <lstPrevious.getCount(); i++)
                if (checked.get(i)) {
                    SuplementItemDTO itemDto = (SuplementItemDTO) previousAdapter.getItem(i);
                    SuplementItemDTO newItem=Helper.Copy(itemDto,true);
                    getEntry().items.add(newItem);
                    newItem.suplementsEntry=getEntry();
                }

            show( true);
        }
    }

    @Override
    protected void show(boolean reload) {
        fillSupplements();
        SuplementsEntryDTO supple=getEntry();
        txtComment.setText(supple.comment);
        tsEntryStatus.setChecked(supple.status == WS_Enums.EntryObjectStatus.Done);
        tsReportStatus.setChecked(supple.reportStatus == WS_Enums.ReportStatus.ShowInReport);
        DateTimeFormatter formatter = DateTimeFormat.longDate();
        tbSupplementHeader.setText(getEntry().trainingDay.trainingDate.toString(formatter));
    }

    void addSupplement(SuplementDTO supplement)
    {
        SuplementsEntryDTO entry=getEntry();
        SuplementItemDTO item = new SuplementItemDTO();
        item.suplement=supplement;
        entry.items.add(item);
        item.suplementsEntry=entry;

        fillSupplements();
        //todo:what's this?
        lstSupplements.getTop();
        Helper.SetSelection(lstSupplements, entry.items.size() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                UUID supplementId=(UUID)data.getSerializableExtra("ItemId");
                SuplementDTO supplement= ObjectsReposidory.getCache().getSupplements().GetItem(supplementId);
                addSupplement(supplement);
            }
        }
    }//onActivityResult

    void advancedEdit()
    {
        if(!isAdvancedEditMode())
        {
            lstSupplements.clearChoices();
            lstSupplements.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            currentActionMode=startSupportActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
                    mnuDelete=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_delete);
                    mnuDelete.setIcon(R.drawable.content_discard);
//                    mnuDelete.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    MenuItemCompat.setShowAsAction(mnuDelete,MenuItem.SHOW_AS_ACTION_ALWAYS);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, android.view.Menu menu) {
                    mode.setTitle(String.format(getString(R.string.advanced_edit_selected_items),0));
                    return false;
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
                    currentActionMode=null;
                }
            })   ;
            currentActionMode.setTitle(String.format(getString(R.string.advanced_edit_selected_items), AndroidHelper.GetCheckedItemsCount(lstSupplements)));
        }
        else
        {
            lstSupplements.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }



        adapter.notifyDataSetChanged();
    }


    private void removeSelectedItems(ActionMode mode) {
        SuplementsEntryDTO entry=getEntry();
        SparseBooleanArray checked =lstSupplements.getCheckedItemPositions();
        for (int i = lstSupplements.getCount()-1; i >=0; i--)
            if (checked.get(i)) {
                SuplementItemDTO itemDto = adapter.getItem(i);
                entry.items.remove(itemDto);
                adapter.remove(itemDto);
            }
        mode.finish();
        adapter.notifyDataSetChanged();
        tbEmptySupplements.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
        lstSupplements.setVisibility(adapter.getCount() > 0 ? View.VISIBLE : View.GONE);
    }

    private boolean isAdvancedEditMode() {
        return lstSupplements.getChoiceMode()==AbsListView.CHOICE_MODE_MULTIPLE;
    }
}
