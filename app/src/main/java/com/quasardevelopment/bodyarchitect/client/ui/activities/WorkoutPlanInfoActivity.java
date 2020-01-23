package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.Cache.TrainingPlansCache;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.TabsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.*;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.util.TrainingBuilder;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import com.viewpagerindicator.PageIndicator;
import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 04.07.13
 * Time: 06:51
 * To change this template use File | Settings | File Templates.
 */
public class WorkoutPlanInfoActivity extends BANormalActivityBase
{
    TabsAdapter mTabsAdapter;
    ViewPager pager;
    TrainingPlan plan;
    PageIndicator mIndicator;
    private android.view.MenuItem mnuRateMe;
    RatingsFragment ratingsFragment;
    WorkoutPlanInfoGeneralFragment generalFragment;
    WorkoutPlanInfoDetailsFragment planFragment;
    private android.view.MenuItem mnuAddToFavorites;
    private ProgressDialog progressDlg;
    private android.view.MenuItem mnuRemoveFromFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_pager_layout, null);
        setMainContent(inflate);
        getSupportActionBar().setSubtitle(R.string.workout_plans_title);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        generalFragment= (WorkoutPlanInfoGeneralFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 0));
        if(generalFragment==null)
        {
            generalFragment=new WorkoutPlanInfoGeneralFragment();
        }
        TextViewFragment descriptionFragment= (TextViewFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 1));
        if(descriptionFragment==null)
        {
            descriptionFragment=new TextViewFragment();
            Bundle bundle = new Bundle(2);
            bundle.putString(WorkoutPlansFragment.TITLE, getString(R.string.workout_plan_header_description));
            bundle.putString(WorkoutPlansFragment.EMPTY_MESSAGE ,getString(R.string.workout_plan_description_empty));
            descriptionFragment.setArguments(bundle);
        }

        planFragment= (WorkoutPlanInfoDetailsFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 2));
        if(planFragment==null)
        {
            planFragment = new WorkoutPlanInfoDetailsFragment();
        }

        ratingsFragment= (RatingsFragment) getSupportFragmentManager().findFragmentByTag(Helper.makeFragmentName(R.id.pager, 3));
        if(ratingsFragment==null && !ApplicationState.getCurrent().isOffline)
        {
            ratingsFragment = new RatingsFragment();
        }


        fragments.add(generalFragment);
        fragments.add(descriptionFragment);
        fragments.add(planFragment);
        if(!ApplicationState.getCurrent().isOffline)
        {
            fragments.add(ratingsFragment);
        }


        pager = (ViewPager) findViewById(R.id.pager);
        mTabsAdapter=new TabsAdapter(getSupportFragmentManager(),fragments);
        pager.setAdapter(mTabsAdapter);


        mIndicator = (PageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {}

            @Override
            public void onPageSelected(int i) {
                supportInvalidateOptionsMenu();
                if(isRatingsTabSelected())
                {
                    ratingsFragment.Fill(plan);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });

        Intent intent=getIntent();
        plan= (TrainingPlan) intent.getSerializableExtra("Item");
        generalFragment.Fill(plan);
        descriptionFragment.Fill(plan.comment);
        planFragment.Fill(plan);

    }

    boolean isRatingsTabSelected()
    {
        return  pager.getCurrentItem()==3;
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        if(isRatingsTabSelected() && !plan.isMine())
        {
            mnuRateMe=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_rate_me);
            mnuRateMe.setIcon(getResources().getDrawable(R.drawable.rating)) ;
//            mnuRateMe.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(mnuRateMe,MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        if(plan.isFavorite())
        {
            mnuRemoveFromFavorites=menu.add(Menu.NONE,3,Menu.NONE,R.string.button_remove_from_favorites);
            mnuRemoveFromFavorites.setIcon(getResources().getDrawable(R.drawable.content_discard)) ;
//            mnuRemoveFromFavorites.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(mnuRemoveFromFavorites,MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        else  if(!plan.isMine())
        {
            mnuAddToFavorites=menu.add(Menu.NONE,2,Menu.NONE,R.string.button_add_to_favorites);
            mnuAddToFavorites.setIcon(getResources().getDrawable(R.drawable.add_to_favorites)) ;
//            mnuAddToFavorites.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(mnuAddToFavorites,MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        if(item==mnuRateMe)
        {
            Intent intent = new Intent(this,VotingActivity.class);
            intent.putExtra("Item", plan);
            startActivityForResult(intent, 1);
        }
        if(mnuAddToFavorites==item)
        {
            favoritesOperation(WS_Enums.SupplementsCycleDefinitionOperation.AddToFavorites);
        }
        if(mnuRemoveFromFavorites==item)
        {
            favoritesOperation(WS_Enums.SupplementsCycleDefinitionOperation.RemoveFromFavorites);
        }
        return true;
    }

    private void favoritesOperation(final WS_Enums.SupplementsCycleDefinitionOperation operation) {
        if (!UpgradeAccountFragment.EnsureAccountType(this))
        {
            return;
        }

        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
            @Override
            public void Wsdl2CodeStartedRequest() {
                progressDlg= BAMessageBox.ShowProgressDlg(WorkoutPlanInfoActivity.this, R.string.progress_retrieving_items);
            }

            @Override
            public void Wsdl2CodeFinished(String methodName, Object Data) {
                TrainingPlansCache cache=ObjectsReposidory.getCache().getTrainingPlans();
                if(operation== WS_Enums.SupplementsCycleDefinitionOperation.RemoveFromFavorites)
                {
                    cache.Remove(plan.globalId);
                }
                else
                {
                    cache.put(plan);
                }
                supportInvalidateOptionsMenu();
            }

            @Override
            public void Wsdl2CodeFinishedWithException(Exception ex) {
                BAMessageBox.ShowToast(R.string.err_unhandled);
            }

            @Override
            public void Wsdl2CodeEndedRequest() {
                hideProgress();
            }
        });
        try {
            WorkoutPlanOperationParam param = new WorkoutPlanOperationParam();
            param.workoutPlanId=plan.globalId;
            param.operation= operation;
            service.WorkoutPlanOperationAsync(param);
        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
            BAMessageBox.ShowToast(R.string.err_unhandled);
        }
    }

    void hideProgress()
    {
        if(progressDlg!=null)
        {
            progressDlg.dismiss();
            progressDlg=null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1)
        {
            if(resultCode == RESULT_OK){

                VoteResult result= (VoteResult) data.getSerializableExtra("Result");
                plan.userRating=result.userRating;
                plan.userShortComment=result.userShortComment;
                plan.rating=result.rating;
                ratingsFragment.UpdateComments();
                generalFragment.Fill(plan);
                TrainingPlansCache cache=ObjectsReposidory.getCache().getTrainingPlans();
                TrainingPlan planFromCache=cache.GetItem(plan.globalId);
                if(planFromCache!=null)
                {
                    planFromCache.userRating=result.userRating;
                    planFromCache.userShortComment=result.userShortComment;
                    planFromCache.rating=result.rating;
                    cache.put(planFromCache);
                }
            }
        }
    }//onActivityResult

    public void UseTrainingPlanDay(final TrainingPlanDay planDay)
    {
        TrainingPlan plan=planDay.trainingPlan;
        if (!plan.isFavorite() && !plan.isMine())
        {
            BAMessageBox.ShowToast(R.string.workout_plan_err_must_add_to_favorites);
            return;
        }

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle(R.string.html_app_name);
        dlgAlert.setMessage(R.string.workout_plan_question_insert_plan_to_today);
        dlgAlert.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                fillStrengthTrainingEntryWithPlan(planDay);
            }
        });
        dlgAlert.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    private void fillStrengthTrainingEntryWithPlan(TrainingPlanDay planDay)
    {
        if (ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().isMine())
        {
            if(!EntryObjectActivityBase.EnsureRemoveEntryTypeFromToday(StrengthTrainingEntryDTO.class))
            {//cancel overwrite
                return;
            }
            StrengthTrainingEntryDTO strengthTraining = new StrengthTrainingEntryDTO();
            strengthTraining.startTime = DateTime.now();
            ApplicationState.getCurrent().getTrainingDay().getTrainingDay().objects.add(strengthTraining);
            strengthTraining.trainingDay = ApplicationState.getCurrent().getTrainingDay().getTrainingDay();
            ApplicationState.getCurrent().CurrentEntryId=new LocalObjectKey(strengthTraining);
            TrainingBuilder builder = new TrainingBuilder();
            builder.FillTrainingFromPlan(planDay, strengthTraining);
            //apply setting related with copy sets data
            builder.PrepareCopiedStrengthTraining(strengthTraining, Settings.getCopyStrengthTrainingMode());
            Intent intent = new Intent(this,StrengthTrainingActivity.class);
            startActivity(intent);
        }

    }
}
