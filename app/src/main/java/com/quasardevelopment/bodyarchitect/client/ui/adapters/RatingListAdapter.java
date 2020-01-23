package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.CommentEntryDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 14.05.13
 * Time: 09:04
 * To change this template use File | Settings | File Templates.
 */
public class RatingListAdapter extends ArrayAdapter<CommentEntryDTO>
{
    public RatingListAdapter(Context context, int textViewResourceId,List<CommentEntryDTO> entries) {
        super(context, textViewResourceId,entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;


        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.rating_list_item, parent, false);
        }

        BAPicture imgPicture = (BAPicture)row.findViewById(R.id.imgUser);
        TextView tbUser = (TextView)row.findViewById(R.id.exercise_view_rating_user);
        RatingBar tbRatingValue = (RatingBar)row.findViewById(R.id.exercise_view_rating_value);
        TextView tbComment = (TextView)row.findViewById(R.id.exercise_view_rating_comment);
        TextView tbDate = (TextView)row.findViewById(R.id.exercise_view_rating_date);


        CommentEntryDTO item=getItem(position);
        tbRatingValue.setRating(item.rating);
        tbComment.setText(item.shortComment);
        tbUser.setText(item.user.userName);
        tbDate.setText(DateTimeHelper.toRelativeDate(item.votedDate));
        imgPicture.fill(item.user.picture);
        return row;
    }
}
