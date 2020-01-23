package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.ObjectNotFoundException;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.InvitationsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import org.joda.time.format.DateTimeFormat;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 13.06.13
 * Time: 08:06
 * To change this template use File | Settings | File Templates.
 */
public class InvitationViewActivity extends BANormalActivityBase
{
    FriendInvitationDTO invitation;
    TextView tbUsername;
    TextView tbDateTime;
    TextView tbMessageFromUser;
    TextView tbOperationMessage;
    BAPicture imgUser;
    private ProgressDialog progressDlg;
    Button btnOk;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_invitation_view, null);
        setMainContent(inflate);

        Intent intent =getIntent();
        invitation= (FriendInvitationDTO) intent.getSerializableExtra("Invitation");

        tbUsername= (TextView) findViewById(R.id.tbUsername);
        tbDateTime= (TextView) findViewById(R.id.tbDateTime);
        tbMessageFromUser= (TextView) findViewById(R.id.tbMessageFromUser);
        tbOperationMessage= (TextView) findViewById(R.id.tbOperationMessage);
        imgUser= (BAPicture) findViewById(R.id.imgUser);
        btnOk = (Button) findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendshipOperation(WS_Enums.InviteFriendOperation.Accept,getString(R.string.invitation_view_activity_question_accept));
            }
        });
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendshipOperation(WS_Enums.InviteFriendOperation.Reject,getString(R.string.invitation_view_activity_question_reject));
            }
        });
        getSupportActionBar().setSubtitle(R.string.invitation_view_activity_title);
    }

    @Override
    protected void onResume() {
        super.onResume();

        tbOperationMessage.setText(InvitationsAdapter.getOperationMessage(invitation));
        tbUsername.setText(invitation.inviter!=null?invitation.inviter.userName:invitation.invited.userName);
        tbDateTime.setText(DateTimeHelper.toLocal(invitation.createdDateTime).toString(DateTimeFormat.mediumDateTime()));
        imgUser.fill(invitation.inviter!=null?invitation.inviter.picture:invitation.invited.picture);
        if(TextUtils.isEmpty(invitation.message))
        {
            tbMessageFromUser.setText(R.string.invitation_view_activity_no_message);
        }
        else
        {
            tbMessageFromUser.setText(String.format("„%s”",invitation.message));
        }
        btnOk.setVisibility(invitation!=null && invitation.inviter!=null && invitation.invitationType.equals(WS_Enums.InvitationType.Invite)?View.VISIBLE:View.INVISIBLE);
        btnCancel.setVisibility(invitation!=null && (invitation.inviter==null || invitation.invitationType.equals(WS_Enums.InvitationType.Invite) || invitation.invitationType.equals(WS_Enums.InvitationType.RejectFriendship))?View.VISIBLE:View.INVISIBLE);
    }

    private void friendshipOperation(final WS_Enums.InviteFriendOperation operation,final String question) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle(R.string.html_app_name);
        dlgAlert.setMessage(question);
        dlgAlert.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                friendshipOperationImplementation(operation);
            }
        });
        dlgAlert.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    void friendshipOperationImplementation(final WS_Enums.InviteFriendOperation operation)
    {
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
            @Override
            public void Wsdl2CodeStartedRequest() {
                progressDlg= BAMessageBox.ShowProgressDlg(InvitationViewActivity.this, R.string.progress_sending);
            }

            @Override
            public void Wsdl2CodeFinished(String methodName, Object Data)
            {
                FriendInvitationDTO existingInvitation= Helper.SingleOrDefault(filter(new Predicate<FriendInvitationDTO>() {
                    public boolean apply(FriendInvitationDTO item) {
                        return (item.inviter==invitation.inviter || (item.inviter!=null && item.inviter.globalId.equals(invitation.inviter.globalId))) && (item.invited==invitation.invited || (item.invited!=null&& item.invited.globalId.equals(invitation.invited.globalId)));
                    }
                }, ApplicationState.getCurrent().getProfileInfo().invitations));
                ApplicationState.getCurrent().getProfileInfo().invitations.remove(existingInvitation);
                ApplicationState.getCurrent().SetModifiedFlag();
                finish();
            }

            @Override
            public void Wsdl2CodeFinishedWithException(Exception ex) {
                if(!(ex instanceof ObjectNotFoundException))
                {
                    BAMessageBox.ShowToast(R.string.err_unhandled);
                }
                else
                {
                    finish();
                }
            }

            @Override
            public void Wsdl2CodeEndedRequest() {
                hideProgress();
            }
        });
        try {
            InviteFriendOperationData data=new InviteFriendOperationData();
            data.operation=operation;
            data.user=invitation.inviter!=null?invitation.inviter:invitation.invited;
            service.InviteFriendOperationAsync(data);
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
}
