package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingPlan;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 04.07.13
 * Time: 06:53
 * To change this template use File | Settings | File Templates.
 */
public class WorkoutPlanInfoGeneralFragment extends Fragment implements TitleProvider
{
    private TextView tbName;
    private TextView tbDifficult;
    private TextView tbTrainingType;
    private TextView tbPurpose;
    private TextView tbCreatedDate;
    private TextView tbPublicationDate;
    BAPicture imgUser;
    RatingBar rating;
    TrainingPlan plan;
    ImageView imgStatus;
    TextView tbUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_workout_plan_general_info, container, false);
        tbName= (TextView) rootView.findViewById(R.id.tbName);
        tbDifficult= (TextView) rootView.findViewById(R.id.tbDifficult);
        tbTrainingType= (TextView) rootView.findViewById(R.id.tbTrainingType);
        tbPurpose= (TextView) rootView.findViewById(R.id.tbPurpose);
        tbPublicationDate= (TextView) rootView.findViewById(R.id.tbPublicationDate);
        tbCreatedDate= (TextView) rootView.findViewById(R.id.tbCreatedDate);
        imgUser= (BAPicture)rootView.findViewById(R.id.imgUser);
        imgStatus= (ImageView)rootView.findViewById(R.id.imgStatus);
        rating= (RatingBar)rootView.findViewById(R.id.rating);
        tbUrl= (TextView) rootView.findViewById(R.id.tbUrl);

        return rootView;
    }
    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getResources().getString(R.string.workout_plan_general_title);
    }

    public void Fill(TrainingPlan plan) {
        this.plan=plan;
    }

    @Override
    public void onResume() {
        super.onResume();

        tbName.setText(plan.name);
        rating.setRating(plan.rating);
        tbDifficult.setText(EnumLocalizer.Translate(plan.difficult));
        tbTrainingType.setText(EnumLocalizer.Translate(plan.trainingType));
        imgUser.fill(plan.profile.picture);
        tbPurpose.setText(EnumLocalizer.Translate(plan.purpose));
        Drawable statusImg=getResources().getDrawable(plan.status.equals(WS_Enums.PublishStatus.Published)?R.drawable.public_status:R.drawable.private_status);
        imgStatus.setImageDrawable(statusImg);
        tbCreatedDate.setText(DateTimeHelper.toRelativeDate(plan.creationDate));
        if(plan.publishDate!=null)
        {
            tbPublicationDate.setText(DateTimeHelper.toRelativeDate(plan.publishDate));
        }
        else
        {
            tbPublicationDate.setText("");
        }
        if(!TextUtils.isEmpty(plan.url))
        {
            tbUrl.setText(plan.url);
        }
        else
        {
            tbUrl.setText("");
        }
    }
}
