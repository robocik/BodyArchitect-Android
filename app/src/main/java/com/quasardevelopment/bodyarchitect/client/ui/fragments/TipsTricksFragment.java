package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quasardevelopment.bodyarchitect.R;
import org.mcsoxford.rss.RSSItem;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 16.08.13
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */
public class TipsTricksFragment extends android.support.v4.app.Fragment {

    RSSItem rssItem;
    int index,count;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_tip_trick, container, false);

        TextView tbHeader= (TextView) rootView.findViewById(R.id.tbHeader);
        TextView tbDescription= (TextView) rootView.findViewById(R.id.tbDescription);
        TextView tbHeaderNumber= (TextView) rootView.findViewById(R.id.tbHeaderNumber);

        index = getArguments().getInt("Position");
        count = getArguments().getInt("Count");
        rssItem =(RSSItem) getArguments().getSerializable("Feed");

        tbHeader.setText(rssItem.getTitle());
        tbDescription.setText(Html.fromHtml(rssItem.getDescription()));
        tbHeaderNumber.setText(String.format("(%d/%d)",index+1,count));

        return rootView;
    }
}
