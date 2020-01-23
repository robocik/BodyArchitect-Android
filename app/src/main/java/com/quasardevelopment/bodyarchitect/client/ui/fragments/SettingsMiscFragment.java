package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.activities.TipsTricksActivity;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import android.widget.Switch;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 20.07.13
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class SettingsMiscFragment extends Fragment implements TitleProvider {

    android.support.v7.widget.SwitchCompat tbScreenLock;

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getString(R.string.settings_misc_fragment_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_settings_misc, container, false);

        tbScreenLock = (android.support.v7.widget.SwitchCompat) rootView.findViewById(R.id.tbScreenLock);
        tbScreenLock.setChecked(Settings.isScreenOrientationLock());
        tbScreenLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Settings.setScreenOrientationLock(checked);
            }
        });

        Button btnClearCache= (Button) rootView.findViewById(R.id.btnClearCache);
        btnClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageLoader.getInstance().clearDiscCache();
                ImageLoader.getInstance().clearMemoryCache();
                ApplicationState.ClearOffline();
                ApplicationState.getCurrent().clearTrainingDays();
                ObjectsReposidory.clear();
                if(TipsTricksActivity.getFilename(getActivity()).exists())
                {
                    TipsTricksActivity.getFilename(getActivity()).delete();
                }
            }
        });
        return rootView;
    }
}
