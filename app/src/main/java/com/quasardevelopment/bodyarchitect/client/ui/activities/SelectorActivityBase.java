package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsCacheBase;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BAGlobalObject;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.IWsdl2CodeEvents;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 26.05.13
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */
public abstract class SelectorActivityBase extends BANormalActivityBase implements IWsdl2CodeEvents
{
    ProgressDialog progressDlg;
    ExpandableListView lvExercises;
    private android.view.MenuItem mnuRefresh;
    TextView tbEmpty;

    protected abstract ObjectsCacheBase GetCache();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View inflate = getLayoutInflater().inflate(R.layout.activity_exercise_selector, null);
        setMainContent(inflate);


        lvExercises= (ExpandableListView) findViewById(R.id.lvExercises);
        tbEmpty= (TextView) findViewById(R.id.tbEmpty);
        lvExercises.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                BAGlobalObject item= (BAGlobalObject) lvExercises.getExpandableListAdapter().getChild(groupPosition,childPosition);
                SelectItem(item);
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    public void SelectItem(BAGlobalObject item)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("ItemId",item.globalId);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){

                UUID exerciseId= (UUID) data.getSerializableExtra("ItemId");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("ItemId",exerciseId);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }
    }//onActivityResult


    @Override
    protected void onResume() {
        super.onResume();
        refreshItems();
    }

    private void refreshItems() {
        final ObjectsCacheBase cache= GetCache();


        if(cache.isLoaded())
        {
            Parcelable state = lvExercises.onSaveInstanceState();
            fillExercises();
            tbEmpty.setVisibility(View.GONE);
            lvExercises.onRestoreInstanceState(state);
        }
        else if(!ApplicationState.getCurrent().isOffline)
        {
            tbEmpty.setText(R.string.empty_items_message);
            tbEmpty.setVisibility(View.VISIBLE);
            progressDlg= BAMessageBox.ShowProgressDlg(this,R.string.progress_retrieving_items);
            progressDlg.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    cache.cancel();
                }
            });
            progressDlg.setCancelable(true);
            cache.LoadAsync(this);
        }
        else
        {
            tbEmpty.setVisibility(View.VISIBLE);
        }
    }

    protected abstract void fillExercises();

    protected void closeProgress()
    {
        if(progressDlg!=null)
        {
            progressDlg.dismiss();
            progressDlg=null;
        }
    }

    @Override
    public void Wsdl2CodeStartedRequest() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data)
    {
        final ObjectsCacheBase cache= GetCache();
        tbEmpty.setVisibility(cache.isLoaded() && cache.getItems().size()>0?View.GONE:View.VISIBLE);
        lvExercises.setVisibility(cache.isLoaded() && cache.getItems().size()>0?View.VISIBLE:View.GONE);
        fillExercises();
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        BAMessageBox.ShowToast(R.string.err_unhandled);
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        closeProgress();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        if(!ApplicationState.getCurrent().isOffline)
        {
            mnuRefresh=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_refresh);
//            mnuRefresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(mnuRefresh,MenuItem.SHOW_AS_ACTION_ALWAYS);
            mnuRefresh.setIcon(R.drawable.navigation_refresh);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item==mnuRefresh)
        {
            ObjectsCacheBase cache= GetCache();
            cache.Clear();
            refreshItems();
        }

        return true;
    }

}
