package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.activities.AccountTypeActivity;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 08.07.13
 * Time: 07:28
 * To change this template use File | Settings | File Templates.
 */
public class MyAccountFragment extends android.support.v4.app.Fragment implements TitleProvider {
    TextView tbPoints;
    private Button btnAccountType;
    private Button btnBuy;

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getString(R.string.my_account_fragment_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_myaccount, container, false);

        tbPoints= (TextView) rootView.findViewById(R.id.tbPoints);
        btnAccountType = (Button) rootView.findViewById(R.id.tbAccountType);
        btnAccountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountTypeActivity activity = (AccountTypeActivity) getActivity();
                activity.ShowChangeAccountPage();
            }
        });
        btnBuy= (Button) rootView.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Settings.getServerUrl() + "V2/Payments.aspx?Token=" +ApplicationState.getCurrent().getSessionData().token.sessionId));
                startActivity(browserIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        fillCurrentLicence();
    }

    void fillCurrentLicence()
    {
        tbPoints.setText(Integer.toString(ApplicationState.getCurrent().getProfileInfo().licence.bAPoints));
        btnAccountType.setText(EnumLocalizer.Translate(ApplicationState.getCurrent().getProfileInfo().licence.accountType));
    }

    public void Refresh() {
        fillCurrentLicence();
    }
}
