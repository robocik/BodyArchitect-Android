package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 04.07.13
 * Time: 07:45
 * To change this template use File | Settings | File Templates.
 */
public class TextViewFragment extends android.support.v4.app.Fragment implements TitleProvider {
    String title;
    private TextView tbDescription;
    String emptyText;
    String text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_text_view, container, false);
        tbDescription= (TextView) rootView.findViewById(R.id.tbDescription);

        title = getArguments().getString(WorkoutPlansFragment.TITLE);
        emptyText = getArguments().getString(WorkoutPlansFragment.EMPTY_MESSAGE);

        return rootView;
    }
    @Override
    public String getTitle() {
        return title;
    }

    public void Fill(String text)
    {
         this.text=text;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(text))
        {
            tbDescription.setText(emptyText);
        }
        else
        {
            tbDescription.setText(text);
        }
    }
}
