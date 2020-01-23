package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 06.07.13
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
 */
public class BodyInstructorMoreFragment extends Fragment implements TitleProvider {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bodyinstructor_more, container, false);

        return rootView;
    }

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getResources().getString(R.string.bodyainstructor_more_fragment_title);
    }
}
