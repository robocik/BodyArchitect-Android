package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.ConsistencyException;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.activities.AccountTypeActivity;
import com.quasardevelopment.bodyarchitect.client.ui.controls.AccountTypeControl;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BasicHttpBinding_IBodyArchitectAccessService;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.IWsdl2CodeEvents;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.ProfileOperationParam;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 08.07.13
 * Time: 08:51
 * To change this template use File | Settings | File Templates.
 */
public class ChangeAccountTypeFragment extends android.support.v4.app.Fragment implements TitleProvider, IWsdl2CodeEvents {

    AccountTypeControl ctrlBasic;
    AccountTypeControl ctrlPremium;
    AccountTypeControl ctrlInstructor;
    ProgressDialog progressDlg;

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getResources().getString(R.string.change_account_type_fragment_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_change_account_type, container, false);

        ctrlBasic= (AccountTypeControl) rootView.findViewById(R.id.ctrlBasic);
        ctrlPremium= (AccountTypeControl) rootView.findViewById(R.id.ctrlPremium);
        ctrlInstructor= (AccountTypeControl) rootView.findViewById(R.id.ctrlInstructor);

        ctrlBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccountType(WS_Enums.AccountType.User);
            }
        });
        ctrlPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccountType(WS_Enums.AccountType.PremiumUser);
            }
        });
        ctrlInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccountType(WS_Enums.AccountType.Instructor);
            }
        });

        Refresh();
        return rootView;
    }

    void changeAccountType(final WS_Enums.AccountType accountType)
    {
        if(ApplicationState.getCurrent().isOffline)
        {
            BAMessageBox.ShowToast(R.string.info_feature_offline_mode);
            return;
        }
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());
        dlgAlert.setTitle(R.string.html_app_name);

        if (ApplicationState.getCurrent().getProfileInfo().licence.accountType.getCode() > accountType.getCode())
        {
            int accountDiff = accountType.getCode() - ApplicationState.getCurrent().getProfileInfo().licence.accountType.getCode();
            int kara = Math.abs(accountDiff) * ApplicationState.getCurrent().getProfileInfo().licence.payments.kara;


            String msg=String.format(getString(R.string.change_account_type_fragment_question_change_to_lower),kara);
            dlgAlert.setMessage(msg);
        }
        else
        {
            dlgAlert.setMessage(R.string.change_account_type_fragment_question_change);
        }

        dlgAlert.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                changeAccountTypeImplementation(accountType);
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

    void hideProgress()
    {
        if(progressDlg!=null)
        {
            progressDlg.dismiss();
            progressDlg=null;
        }
    }

    void changeAccountTypeImplementation(WS_Enums.AccountType accountType)
    {
        progressDlg= BAMessageBox.ShowProgressDlg(getActivity(),R.string.change_account_type_progress);

        try{
            BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(this);
            ProfileOperationParam param = new ProfileOperationParam();
            param.accountType =accountType;
            param.profileId=ApplicationState.getCurrent().getSessionData().profile.globalId;
            param.operation= WS_Enums.ProfileOperation.AccountType;
            service.ProfileOperationAsync(param);

        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
            BAMessageBox.ShowToast(R.string.err_unhandled);
        }
    }

    @Override
    public void Wsdl2CodeStartedRequest() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        AccountTypeActivity activity = (AccountTypeActivity) getActivity();
        activity.refreshProfile();
    }



    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        if(ex instanceof ConsistencyException)
        {
            BAMessageBox.ShowToast(R.string.change_account_type_fragment_err_not_enought_points);
        }
        else
        {
            BAMessageBox.ShowToast(R.string.err_unhandled);
        }
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        hideProgress();
    }

    public void Refresh() {
        ctrlBasic.Fill(WS_Enums.AccountType.User, 0,R.string.change_account_type_fragment_basic_account_description, ApplicationState.getCurrent().getProfileInfo().licence.accountType.equals(WS_Enums.AccountType.User));
        ctrlPremium.Fill(WS_Enums.AccountType.PremiumUser,1,R.string.change_account_type_fragment_premium_account_description,ApplicationState.getCurrent().getProfileInfo().licence.accountType.equals(WS_Enums.AccountType.PremiumUser));
        ctrlInstructor.Fill(WS_Enums.AccountType.Instructor, 2, R.string.change_account_type_fragment_instructor_account_description, ApplicationState.getCurrent().getProfileInfo().licence.accountType.equals(WS_Enums.AccountType.Instructor));
    }
}
