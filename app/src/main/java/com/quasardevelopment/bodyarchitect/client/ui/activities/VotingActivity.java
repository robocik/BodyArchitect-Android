package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.IRatingable;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 15.05.13
 * Time: 07:38
 * To change this template use File | Settings | File Templates.
 */
public class VotingActivity extends BANormalActivityBase implements IWsdl2CodeEvents
{
    IRatingable item;
    RatingBar rating;
    EditText txtComment;
    ProgressDialog progressDlg;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_voting, null);
        setMainContent(inflate);

        rating = (RatingBar) findViewById(R.id.voting_activity_rating);
        TextView tbName = (TextView) findViewById(R.id.voting_activity_name);
        txtComment = (EditText) findViewById(R.id.voting_activity_comment);

        getSupportActionBar().setSubtitle(R.string.votings_activity_title);
        Intent intent=getIntent();
        item= (IRatingable) intent.getSerializableExtra("Item");
        rating.setRating(item.getUserRating());
        txtComment.setText(item.getUserShortComment());
        tbName.setText(item.getName());

        Button btnSave= (Button)findViewById(R.id.voting_activity_btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    showProgress(true);
                    BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(VotingActivity.this);
                    VoteParams param = new VoteParams();
                    param.globalId=item.getGlobalId();
                    param.userRating=rating.getRating();
                    param.userShortComment=txtComment.getText().toString();
                    param.objectType=getObjectType(item);
                    service.VoteAsync(param);
                }
                catch(NetworkErrorException ex)
                {
                    showProgress(false);
                    BAMessageBox.ShowToast(R.string.err_network_problem);
                }
                catch(Exception ex)
                {
                    showProgress(false);
                    BAMessageBox.ShowToast(R.string.err_unhandled);
                }
            }
        });
    }

    void showProgress(boolean show)
    {
        if(show)
        {
            progressDlg= BAMessageBox.ShowProgressDlg(VotingActivity.this, R.string.progress_sending);
        }
        else   if(progressDlg!=null)
        {
            progressDlg.dismiss();
            progressDlg=null;
        }
    }

    WS_Enums.VoteObject getObjectType(IRatingable item)
    {
        if(item.getClass().equals(ExerciseDTO.class))
        {
            return WS_Enums.VoteObject.Exercise;
        }
        if(item.getClass().equals(SuplementDTO.class))
        {
            return WS_Enums.VoteObject.Supplement;
        }
        if(item.getClass().equals(SupplementCycleDefinitionDTO.class))
        {
            return WS_Enums.VoteObject.SupplementCycleDefinition;
        }
        return WS_Enums.VoteObject.WorkoutPlan;
    }

    @Override
    public void Wsdl2CodeStartedRequest() { }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        VoteResult result=(VoteResult)Data;
        result.userRating=rating.getRating();
        result.userShortComment=txtComment.getText().toString();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("Result", result);
        setResult(Activity.RESULT_OK, returnIntent);


        finish();
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        BAMessageBox.ShowToast(R.string.err_unhandled);
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        showProgress(false);
    }
}
