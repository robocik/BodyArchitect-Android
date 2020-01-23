package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.ui.controls.VotesControl;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.ExerciseDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.VoteResult;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 13.05.13
 * Time: 21:13
 * To change this template use File | Settings | File Templates.
 */
public class ExerciseDetailActivity extends BANormalActivityBase
{
    TabHost tabHost;
    ExerciseDTO exercise;
    VotesControl votesCtrl;
    android.view.MenuItem mnuAdd;
    android.view.MenuItem mnuRateMe;
    TextView tbExerciseName;
    TextView tbForce;
    TextView tbMechanic;
    TextView tbDifficult;
    TextView tbExerciseType;
    TextView tbUrl;
    RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View inflate = getLayoutInflater().inflate(R.layout.activity_exercise_view, null);
        setMainContent(inflate);
        prepareTabs();

        Intent intent=getIntent();
        UUID exerciseId= (UUID) intent.getSerializableExtra("ItemId");
        exercise=ObjectsReposidory.getCache().getExercises().GetItem(exerciseId);

        tbExerciseName= (TextView) findViewById(R.id.exercise_view_exercise_name);
        tbForce= (TextView) findViewById(R.id.exercise_view_force);
        tbMechanic= (TextView) findViewById(R.id.exercise_view_mechanic);
        tbDifficult= (TextView) findViewById(R.id.exercise_view_difficult);
        tbExerciseType= (TextView) findViewById(R.id.exercise_view_type);
        tbUrl= (TextView) findViewById(R.id.exercise_view_url);
        rating= (RatingBar) findViewById(R.id.exercise_view_rating);

        votesCtrl= (VotesControl) findViewById(R.id.votesCtrl);

        fillExercise();
    }

    private void fillExercise() {
        tbExerciseName.setText(exercise.name);
        getSupportActionBar().setSubtitle(exercise.shortcut.toUpperCase());
        tbForce.setText(EnumLocalizer.Translate(exercise.exerciseForceType));
        tbMechanic.setText(EnumLocalizer.Translate(exercise.mechanicsType));
        tbDifficult.setText(EnumLocalizer.Translate(exercise.difficult));
        tbExerciseType.setText(EnumLocalizer.Translate(exercise.exerciseType));
        rating.setRating(exercise.rating);
        tbUrl.setText(exercise.url);

        loadComments();
    }

    void prepareTabs()
    {
        tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s)
            {
                loadComments();
            }
        });
        TabHost.TabSpec spec1=tabHost.newTabSpec(getString(R.string.exercise_view_activity_tab_info));
        spec1.setContent(R.id.exercise_view_activity_tab_info);
        spec1.setIndicator(getString(R.string.exercise_view_activity_tab_info));


        TabHost.TabSpec spec2=tabHost.newTabSpec(getString(R.string.exercise_view_activity_tab_ratings));
        spec2.setIndicator(getString(R.string.exercise_view_activity_tab_ratings));
        spec2.setContent(R.id.exercise_view_activity_tab_vote);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        mnuAdd=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_add);
        mnuAdd.setIcon(getResources().getDrawable(R.drawable.content_new)) ;
//        mnuAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        MenuItemCompat.setShowAsAction(mnuAdd,MenuItem.SHOW_AS_ACTION_ALWAYS);

        if(!exercise.isMine())
        {
            mnuRateMe=menu.add(Menu.NONE,2,Menu.NONE,R.string.button_rate_me);
            mnuRateMe.setIcon(getResources().getDrawable(R.drawable.rating)) ;
//            mnuRateMe.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(mnuRateMe,MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item==mnuAdd)
        {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("ItemId",exercise.globalId);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        else if(item==mnuRateMe)
        {
            Intent intent = new Intent(this,VotingActivity.class);
            intent.putExtra("Item",exercise);
            startActivityForResult(intent,1);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){

                VoteResult result= (VoteResult) data.getSerializableExtra("Result");
                exercise.userRating=result.userRating;
                exercise.userShortComment=result.userShortComment;
                exercise.rating=result.rating;
                votesCtrl.UpdateComments();
                fillExercise();
            }
        }
    }//onActivityResult

    void loadComments()
    {
        if(tabHost.getCurrentTab()==1 )
        {
            votesCtrl.Load(exercise);
        }
    }
}
