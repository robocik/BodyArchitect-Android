package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.MenuItem;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.ObjectNotFoundException;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.activities.*;
import com.quasardevelopment.bodyarchitect.client.ui.controls.AwardsControl;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Constants;
import com.quasardevelopment.bodyarchitect.client.util.Functions;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 19.04.13
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
public class MyProfileFragment extends Fragment implements IWsdl2CodeEvents {

    BAPicture imgMe;
    TextView tbUsername;
    TextView tbPoints;
    TextView tbStatus;
    TextView tbOffline;
    LinearLayout pnlUserInfo;
    private MenuItem mnuMyStatus;
    private MenuItem mnuGoOnline;
    ProfileInformationDTO profileInfo;
    TextView btnMessages;
    TextView btnInvitations;
    TextView btnSynchronize;

    ImageButton btnEditProfile;
    AwardsControl ctrlAwards;
    private MenuItem mnuRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_myprofile, container, false);

        tbUsername= (TextView) rootView.findViewById(R.id.tbUsername);
        tbPoints= (TextView) rootView.findViewById(R.id.tbPoints);
        tbStatus= (TextView) rootView.findViewById(R.id.tbStatus);
        tbOffline= (TextView) rootView.findViewById(R.id.tbOffline);
        btnMessages= (TextView) rootView.findViewById(R.id.btnMessages);
        btnInvitations= (TextView) rootView.findViewById(R.id.btnInvitations);
        TextView btnStatistics=(TextView)rootView.findViewById(R.id.btnStatistics);
        TextView btnMyAccount=(TextView)rootView.findViewById(R.id.btnMyAccount);
        pnlUserInfo= (LinearLayout) rootView.findViewById(R.id.pnlUserInfo);
        btnEditProfile= (ImageButton) rootView.findViewById(R.id.btnEditProfile);

        pnlUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AccountTypeActivity.class);
                startActivity(intent);
            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });
        btnSynchronize= (TextView) rootView.findViewById(R.id.btnSynchronize);
        imgMe= (BAPicture) rootView.findViewById(R.id.image);
        imgMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });
        ctrlAwards= (AwardsControl) rootView.findViewById(R.id.ctrlAwards);

        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StatisticsActivity.class);
                intent.putExtra("User",ApplicationState.getCurrent().getProfileInfo().user);
                startActivity(intent);
            }
        });
        btnMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AccountTypeActivity.class);
                startActivity(intent);
            }
        });

        btnSynchronize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SynchronizationActivity.class);
                startActivity(intent);
            }
        });

        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MessagesActivity.class);
                intent.putExtra("SelectedTab",0); //select messages tab
                startActivity(intent);
            }
        });

        btnInvitations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MessagesActivity.class);
                intent.putExtra("SelectedTab",1);//select invitations tab
                startActivity(intent);
            }
        });
        setHasOptionsMenu(true);
        return rootView;

    }

    void editProfile()
    {
        if(ApplicationState.getCurrent().isOffline)
        {
            return;
        }
        ApplicationState.getCurrent().editProfileInfo = Helper.Copy(ApplicationState.getCurrent().getProfileInfo());
        if(ApplicationState.getCurrent().editProfileInfo.wymiary==null)
        {
            ApplicationState.getCurrent().editProfileInfo.wymiary=new WymiaryDTO();
            ApplicationState.getCurrent().editProfileInfo.wymiary.time.dateTime = DateTime.now();
        }
         Intent intent = new Intent(this.getActivity(), ProfileEditActivity.class);
         startActivity(intent);
    }
    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(!ApplicationState.getCurrent().isOffline)
        {
            mnuMyStatus=menu.add(Menu.NONE, 0, Menu.NONE,R.string.myprofile_menu_status);
            mnuRefresh=menu.add(Menu.NONE, 0, Menu.NONE,R.string.menu_refresh);
        }

        if(Constants.IsDebugMode)
        {
            mnuGoOnline=menu.add(Menu.NONE, 0, Menu.NONE, ApplicationState.getCurrent().isOffline ? "Go online" : "Go offline");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item==mnuMyStatus)
        {
            changeStatus();
            return true;
        }
        if(item==mnuGoOnline )
        {
            goOfflineMode();
            return true;
        }
        if(item==mnuRefresh )
        {
            refreshData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void refreshData()
    {
        final BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
            public ProgressDialog progressDlg;

            @Override
            public void Wsdl2CodeStartedRequest() {
                progressDlg= BAMessageBox.ShowProgressDlg(getActivity(), R.string.progress_retrieving_profile_info);
            }

            @Override
            public void Wsdl2CodeFinished(String methodName, Object Data) {
                fill(ApplicationState.getCurrent().getProfileInfo());
            }

            @Override
            public void Wsdl2CodeFinishedWithException(Exception ex) {
                BAMessageBox.ShowError(R.string.err_retrieving_profile_info,getActivity());
            }

            @Override
            public void Wsdl2CodeEndedRequest() {
                progressDlg. dismiss();
            }
        }) ;
        service.executeAsync(new Functions.IFunc<Object>() {
            @Override
            public Object Func() throws Exception {
                GetProfileInformationCriteria param = new GetProfileInformationCriteria();
                ProfileInformationDTO info=service.GetProfileInformation(param);
                ApplicationState.getCurrent().setProfileInfo(info);
                ObjectsReposidory.getCache().getMessages().Refresh(service);
                return true;
            }
        },"Test",false);
    }

    private void goOfflineMode() {
        if(ApplicationState.getCurrent().isOffline)
        {
            //todo:finish go to online mode
            getActivity().supportInvalidateOptionsMenu();
        }
        else
        {
            ApplicationState.getCurrent().SaveState(false);
            try {
                ApplicationState.goToOfflineMode();
            } catch (ObjectNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                BAMessageBox.ShowToast("Cannot go to offline mode");
            }
        }

        updateOfflineGUI();
    }

    private void changeStatus()
    {
       ChangeStatusFragment dlg = new ChangeStatusFragment();
       FragmentManager fm = this.getChildFragmentManager();
       dlg.setTargetFragment(this,5);
        dlg.setStyle(DialogFragment.STYLE_NORMAL,R.style.BADialog);
        Bundle params = new Bundle();
        params.putString("Status",profileInfo.user.statistics.status.status);
        dlg.setArguments(params);
        dlg.show(fm, "fragment_edit_name");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==5)
        {
            profileInfo.user.statistics.status.status=data.getStringExtra("Status");
            fillStatus();
        }
        super.onActivityResult(requestCode, resultCode, data);    //To change body of overridden methods use File | Settings | File Templates.
    }

    void fillStatus()
    {
        String status=profileInfo!=null?profileInfo.user.statistics.status.status:null;
        tbStatus.setText(status!=null?String.format("„%s”",status):"");
    }

    void updateMessagesGUI()
    {
        if(!isAdded())
        {
            return;
        }
        boolean isMessagesLoaded=ObjectsReposidory.getCache().getMessages().isLoaded();
        btnMessages.setText(String.format(getString(R.string.myprofile_link_messages),isMessagesLoaded?ObjectsReposidory.getCache().getMessages().getItems().size():0));
        btnInvitations.setText(String.format(getString(R.string.myprofile_link_invitations),profileInfo.invitations.size()));

        btnInvitations.setVisibility(isMessagesLoaded && ApplicationState.getCurrent().getProfileInfo().invitations.size()>0?View.VISIBLE:View.GONE);
        btnMessages.setVisibility(isMessagesLoaded && ObjectsReposidory.getCache().getMessages().getItems().size()>0?View.VISIBLE:View.GONE);
    }



    public void fill(ProfileInformationDTO profileInfo)
     {

         this.profileInfo=profileInfo;
         updateOfflineGUI();
         if(profileInfo!=null)
         {
             tbUsername.setText(profileInfo.user.userName);
             tbPoints.setText(Integer.toString(profileInfo.licence.bAPoints));
             imgMe.fill(profileInfo.user.picture);
             ctrlAwards.Fill(profileInfo.user);

             if(!ObjectsReposidory.getCache().getMessages().isLoaded())
             {
                 ObjectsReposidory.getCache().getMessages().LoadAsync(new IWsdl2CodeEvents() {
                     @Override
                     public void Wsdl2CodeStartedRequest() { }

                     @Override
                     public void Wsdl2CodeFinished(String methodName, Object Data) {
                     }

                     @Override
                     public void Wsdl2CodeFinishedWithException(Exception ex)
                     {
                     }

                     @Override
                     public void Wsdl2CodeEndedRequest() {
                         updateMessagesGUI();
                     }
                 });
             }
             else
             {
                 updateMessagesGUI();
             }
         }
         else
         {
             ctrlAwards.Fill(null);
             tbUsername.setText("");
             tbPoints.setText("");
             imgMe.fill(null);
             btnMessages.setVisibility(View.GONE);
             btnInvitations.setVisibility(View.GONE);
             btnSynchronize.setVisibility(View.GONE);
         }
         fillStatus();
     }

    void updateOfflineGUI()
    {
        if(ApplicationState.getCurrent()==null)
        {
            return;
        }
        tbOffline.setVisibility(ApplicationState.getCurrent().isOffline?View.VISIBLE:View.GONE);
        btnEditProfile.setVisibility(!ApplicationState.getCurrent().isOffline ?View.VISIBLE:View.GONE);
        int localModified=ApplicationState.getCurrent().getLocalModified().size();
        btnSynchronize.setVisibility(!ApplicationState.getCurrent().isOffline && localModified>0?View.VISIBLE:View.GONE);
        btnSynchronize.setText(String.format(getString(R.string.myprofile_link_synchronization),localModified));
    }

    @Override
    public void onResume() {
        super.onResume();
        fill(ApplicationState.getCurrent().getProfileInfo());
    }


    @Override
    public void Wsdl2CodeStartedRequest() {
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data)
    {
        Bitmap img=(Bitmap)Data;
        imgMe.setImageBitmap(img);
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex)
    {
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
    }
}
