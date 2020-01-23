package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.IRatingable;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.controls.VotesControl;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 04.07.13
 * Time: 07:55
 * To change this template use File | Settings | File Templates.
 */
public class RatingsFragment extends Fragment implements TitleProvider {
    VotesControl votesControl;
    IRatingable ratingable;

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getResources().getString(R.string.rating_fragment_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_ratings, container, false);
        votesControl= (VotesControl) rootView.findViewById(R.id.votesCtrl);
        return rootView;
    }

    public void Fill(IRatingable plan) {
        ratingable=plan;
        if(!votesControl.isLoaded() || votesControl.isRefreshRequired())
        {
            votesControl.Load(ratingable);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void UpdateComments() {
        votesControl.UpdateComments();
        votesControl.Load(ratingable);
    }
}
