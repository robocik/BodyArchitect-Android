package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.controls.MeasurementsControl;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WymiaryDTO;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 14.06.13
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class ProfileInfoSizesFragment extends Fragment implements TitleProvider {
    TextView tbStatus;
    MeasurementsControl sizesCtrl;
    WymiaryDTO wymiary;

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getResources().getString(R.string.profile_info_sizes_fragment_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile_info_sizes, container, false);
        sizesCtrl= (MeasurementsControl) rootView.findViewById(R.id.sizesCtrl);
        tbStatus= (TextView) rootView.findViewById(R.id.tbStatus);
        return rootView;
    }

    //null means offline mode
    public void Fill(WymiaryDTO wymiary)
    {
        this.wymiary=wymiary;
        //updateGUI();
    }

    @Override
    public void onResume() {
        super.onResume();

        updateGUI();
    }

    void updateGUI()
    {
        if(wymiary==null)
        {
            tbStatus.setText(ApplicationState.getCurrent().isOffline?R.string.info_feature_offline_mode:R.string.profile_info_sizes_fragment_empty_measurements);
            sizesCtrl.setVisibility(View.GONE);
            tbStatus.setVisibility(View.VISIBLE);
            return;
        }
        tbStatus.setVisibility(View.GONE);
        if( wymiary!=null && !wymiary.isEmpty())
        {
            sizesCtrl.fill(wymiary,null);
        }
        sizesCtrl.setReadOnly(true);

    }
}
