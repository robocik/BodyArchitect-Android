package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.activities.AccountTypeActivity;
import com.quasardevelopment.bodyarchitect.client.ui.activities.AccountTypeDescriptionActivity;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 07.08.13
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
public class UpgradeAccountFragment  extends DialogFragment  {
    private TextView txtDescription;
    private Button btnChangeAccount;
    private Button btnClose;
    private Button btnShowPremium;
    private Button btnShowInstructor;
    WS_Enums.AccountType accountType;

    public static boolean EnsureAccountType(Context context, WS_Enums.AccountType accountType) {
        if(!CheckAccountType(accountType))
        {
            FragmentManager fm = ((android.support.v4.app.FragmentActivity)context).getSupportFragmentManager();
            UpgradeAccountFragment dlg = new UpgradeAccountFragment();
            dlg.setAccountType(accountType);
            dlg.setStyle(DialogFragment.STYLE_NORMAL,R.style.BADialog);
            dlg.show(fm, "fragment_edit_name");
            return false;
        }
        return true;
    }

    public static boolean CheckAccountType(WS_Enums.AccountType accountType)
    {
        return ApplicationState.getCurrent().getProfileInfo().licence.currentAccountType.ordinal()>=(int)accountType.ordinal();
    }
    public static boolean EnsureAccountType(Context context) {
        return EnsureAccountType(context, WS_Enums.AccountType.PremiumUser);
    }

    public UpgradeAccountFragment() {
    }

    public void setAccountType(WS_Enums.AccountType accountType) {
         this.accountType=accountType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upgrade_account, container);

        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        getDialog().setTitle(R.string.html_app_name);

        btnChangeAccount = (Button)view.findViewById(R.id.btnChangeAccount);
        btnClose = (Button)view.findViewById(R.id.btnClose);
        btnShowPremium = (Button)view.findViewById(R.id.btnShowPremium);
        btnShowInstructor = (Button)view.findViewById(R.id.btnShowInstructor);

        btnChangeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AccountTypeActivity.class);
                dismiss();
                startActivity(intent);
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnShowPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAccountDetails();
            }
        });
        btnShowInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAccountDetails();
            }
        });

        txtDescription.setText(accountType.equals(WS_Enums.AccountType.Instructor)?R.string.fragment_upgrade_instructor_account_description:R.string.fragment_upgrade_premium_account_description);
        btnShowPremium.setVisibility(accountType.equals(WS_Enums.AccountType.Instructor)?View.GONE:View.VISIBLE);
        btnShowInstructor.setVisibility(accountType.equals(WS_Enums.AccountType.Instructor)?View.VISIBLE:View.GONE);
        return view;
    }


    void showAccountDetails()
    {
        dismiss();
        Intent intent =new Intent(getActivity(), AccountTypeDescriptionActivity.class);
        intent.putExtra("AccountType",accountType);
        getActivity().startActivity(intent);
    }
}