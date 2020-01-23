package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.ProfileInformationDTO;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 14.06.13
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
public class ProfileInfoAboutFragment extends Fragment implements TitleProvider {
    TextView tbStatus;
    TextView tbDescription;
    ProfileInformationDTO profileInfo;

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getResources().getString(R.string.profile_info_about_fragment_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile_info_about, container, false);
        tbDescription= (TextView) rootView.findViewById(R.id.tbDescription);
        tbStatus= (TextView) rootView.findViewById(R.id.tbStatus);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(profileInfo!=null)
        {
            updateGUI();
        }
    }

    //null means offline mode
    public void Fill(ProfileInformationDTO profileInfo)
    {
        this.profileInfo=profileInfo;
        updateGUI();
    }

    void updateGUI()
    {
        if(profileInfo==null)
        {
            tbStatus.setText(R.string.info_feature_offline_mode);
            tbDescription.setVisibility(View.GONE);
            return;
        }
        if( !TextUtils.isEmpty(profileInfo.aboutInformation))
        {
            tbDescription.setText(profileInfo.aboutInformation);
            tbDescription.setVisibility(View.VISIBLE);
            tbStatus.setVisibility(View.GONE);
        }
        else
        {
            tbDescription.setVisibility(View.GONE);
            tbStatus.setVisibility(View.VISIBLE);
            tbStatus.setText(R.string.profile_info_about_fragment_empty_message);
        }
    }
}
