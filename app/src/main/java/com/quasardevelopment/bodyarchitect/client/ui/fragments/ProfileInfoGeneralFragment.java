package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.AchievementsModel;
import com.quasardevelopment.bodyarchitect.client.model.Country;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDaysHolder;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.activities.CalendarView;
import com.quasardevelopment.bodyarchitect.client.ui.activities.SendMessageActivity;
import com.quasardevelopment.bodyarchitect.client.ui.activities.StatisticsActivity;
import com.quasardevelopment.bodyarchitect.client.ui.controls.AwardsControl;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import com.splunk.mint.Mint;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 12.06.13
 * Time: 07:14
 * To change this template use File | Settings | File Templates.
 */
public class ProfileInfoGeneralFragment extends Fragment implements TitleProvider {
    TextView tbUsername;
    TextView tbCountry;
    TextView tbGender;
    TextView tbDateTime;
    TextView tbStatus;
    TextView tbAwards;
    BAPicture imgUser;
    Button btnSendMessage;
    Button btnStatistics;
    Button btnShowCalendar;
    TextView tbLastEntryDate;
    Button btnAddToFavorites;
    Button btnSendInvitation;
    UserSearchDTO user;
    Button btnRejectFriendship;
    Button btnRemoveFromFavorites;
    ProgressDialog progressDlg;
    AwardsControl ctrlAwards;

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getResources().getString(R.string.profile_info_general_fragment_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile_info_general, container, false);
        tbUsername= (TextView) rootView.findViewById(R.id.tbUsername);
        tbGender= (TextView) rootView.findViewById(R.id.tbGender);
        tbCountry= (TextView) rootView.findViewById(R.id.tbCountry);
        tbDateTime= (TextView) rootView.findViewById(R.id.tbDateTime);
        tbLastEntryDate= (TextView) rootView.findViewById(R.id.tbLastEntryDate);
        btnStatistics= (Button) rootView.findViewById(R.id.btnStatistics);
        btnShowCalendar= (Button)rootView.findViewById(R.id.btnShowCalendar);
        btnAddToFavorites= (Button)rootView.findViewById(R.id.btnAddToFavorites);
        btnSendInvitation= (Button)rootView.findViewById(R.id.btnSendInvitation);
        btnRejectFriendship= (Button)rootView.findViewById(R.id.btnRejectFriendship);
        btnSendMessage= (Button)rootView.findViewById(R.id.btnSendMessage);
        btnRemoveFromFavorites=(Button)rootView.findViewById(R.id.btnRemoveFromFavorites);
        tbStatus= (TextView) rootView.findViewById(R.id.tbStatus);
        imgUser= (BAPicture) rootView.findViewById(R.id.imgUser);
        ctrlAwards= (AwardsControl) rootView.findViewById(R.id.ctrlAwards);
        tbAwards= (TextView) rootView.findViewById(R.id.tbAwards);

        btnShowCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar();
            }
        });
        btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteOperationImplementation(WS_Enums.FavoriteOperation.Add);
            }
        });
        btnRemoveFromFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteOperationImplementation(WS_Enums.FavoriteOperation.Remove);
            }
        });
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        btnSendInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendshipOperation(WS_Enums.InviteFriendOperation.Invite,R.string.profile_info_general_fragment_question_send_invitation);
            }
        });
        btnRejectFriendship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendshipOperation(WS_Enums.InviteFriendOperation.Reject,R.string.profile_info_general_fragment_question_remove_friend);
            }
        });
        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StatisticsActivity.class);
                intent.putExtra("User",user);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void showCalendar() {
        ApplicationState.getCurrent().setCurrentBrowsingTrainingDays(new TrainingDaysHolder(user));
        Intent intent = new Intent(getActivity(),CalendarView.class);
        startActivity(intent);
    }

    private void sendMessage() {
        Intent intent = new Intent(getActivity(),SendMessageActivity.class);
        intent.putExtra("Receiver",user);
        startActivity(intent);
    }


    void favoriteOperationImplementation(final WS_Enums.FavoriteOperation operation)
    {
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
            @Override
            public void Wsdl2CodeStartedRequest() {
                progressDlg=BAMessageBox.ShowProgressDlg(getActivity(),R.string.progress_sending);
            }

            @Override
            public void Wsdl2CodeFinished(String methodName, Object Data) {
                if(operation== WS_Enums.FavoriteOperation.Remove)
                {
                    Helper.RemoveFromCollection(user,ApplicationState.getCurrent().getProfileInfo().favoriteUsers);
                }
                else
                {
                    ApplicationState.getCurrent().getProfileInfo().favoriteUsers.add(user);
                }
                ApplicationState.getCurrent().SetModifiedFlag();
                updateGUI();
            }

            @Override
            public void Wsdl2CodeFinishedWithException(Exception ex) {
                Mint.logException(ex);
                BAMessageBox.ShowToast(R.string.err_unhandled);
            }

            @Override
            public void Wsdl2CodeEndedRequest() {
                hideProgress();
            }
        });
        try {
            service.UserFavoritesOperationAsync(user, operation);
        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
            BAMessageBox.ShowToast(R.string.err_unhandled);
        }
    }

    void friendshipOperationImplementation(final WS_Enums.InviteFriendOperation operation)
    {
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
            @Override
            public void Wsdl2CodeStartedRequest() {

                progressDlg=BAMessageBox.ShowProgressDlg(getActivity(),R.string.progress_sending);
            }

            @Override
            public void Wsdl2CodeFinished(String methodName, Object Data) {
                if(operation== WS_Enums.InviteFriendOperation.Reject)
                {
                    Helper.RemoveFromCollection(user,ApplicationState.getCurrent().getProfileInfo().friends);
                }
                else
                {
                    ApplicationState.getCurrent().getProfileInfo().invitations.add((FriendInvitationDTO) Data);
                }
                ApplicationState.getCurrent().SetModifiedFlag();
                updateGUI();
            }

            @Override
            public void Wsdl2CodeFinishedWithException(Exception ex) {
                Mint.logException(ex);
                BAMessageBox.ShowToast(R.string.err_unhandled);
            }

            @Override
            public void Wsdl2CodeEndedRequest() {
                hideProgress();
            }
        });
        try {
            InviteFriendOperationData data=new InviteFriendOperationData();
            data.operation=operation;
            data.user=user;
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

    private void friendshipOperation(final WS_Enums.InviteFriendOperation operation,final int question) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());
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

    public void Fill(UserSearchDTO user)
    {
         this.user=user;
    }

    boolean hasAwards()
    {
        return !AchievementsModel.Achievements.GetGreenStar(user) .equals(AchievementsModel.AchievementStar.None) || !AchievementsModel.Achievements.GetRedStar(user) .equals(AchievementsModel.AchievementStar.None) ||
                !AchievementsModel.Achievements.GetBlueStar(user).equals(AchievementsModel.AchievementStar.None);
    }

    void updateGUI()
    {
        tbUsername.setText(user.userName);
        tbGender.setText(EnumLocalizer.Translate(user.gender));
        tbDateTime.setText(DateTimeHelper.toRelativeDate(DateTimeHelper.toLocal(user.creationDate)));
        tbCountry.setText(Country.getCountry(user.countryId).EnglishName);
        imgUser.fill(user.picture);
        ctrlAwards.Fill(user);

        if(hasAwards())
        {
            tbAwards.setVisibility(View.VISIBLE);
            ctrlAwards.setVisibility(View.VISIBLE);
        }
        else
        {
            tbAwards.setVisibility(View.GONE);
            ctrlAwards.setVisibility(View.GONE);
        }
        if(user.statistics.lastEntryDate!=null)
        {
            tbLastEntryDate.setText(DateTimeHelper.toRelativeDate(user.statistics.lastEntryDate));
            tbLastEntryDate.setVisibility(View.VISIBLE);
        }
        else
        {
            tbLastEntryDate.setVisibility(View.GONE);
        }



        if(user.haveAccess(user.privacy.calendarView))
        {
            tbLastEntryDate.setVisibility(View.VISIBLE);
            btnShowCalendar .setVisibility(View.VISIBLE);
        }
        else
        {
            tbLastEntryDate.setVisibility(View.GONE);
            btnShowCalendar .setVisibility(View.GONE);
        }
        boolean isOffline=ApplicationState.getCurrent().isOffline;
        btnRejectFriendship.setVisibility(!isOffline && user.isFriend()?View.VISIBLE:View.GONE);
        boolean canBeFriend=!user.isFriend() && !user.isInvited();
        btnSendInvitation.setVisibility(!isOffline && canBeFriend?View.VISIBLE:View.GONE);
        boolean canBeFavorite=!user.isFriend() && !user.isFavorite();
        btnAddToFavorites.setVisibility(!isOffline && canBeFavorite?View.VISIBLE:View.GONE);
        btnRemoveFromFavorites.setVisibility(!isOffline && user.isFavorite()?View.VISIBLE:View.GONE);
        btnSendMessage.setVisibility(!isOffline && !user.isDeleted?View.VISIBLE:View.GONE);
        btnShowCalendar.setVisibility(!isOffline && user.haveAccess(user.privacy.calendarView) ? View.VISIBLE : View.GONE);
        if(user.statistics.status!=null && !TextUtils.isEmpty(user.statistics.status.status))
        {
            tbStatus.setText(String.format("„%s”",user.statistics.status.status));
            tbStatus.setVisibility(View.VISIBLE);
        }
        else
        {
            tbStatus.setVisibility(View.GONE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        updateGUI();
    }
}
