package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.AchievementsModel;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.UserSearchDTO;


public class AwardsControl extends LinearLayout
{

    public AwardsControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void Fill(UserSearchDTO user)
    {
        removeAllViews();

        Drawable img=getRedStar(user);
        if(img!=null)
        {
            ImageView imgRed=new ImageView(getContext());
            imgRed.setImageDrawable(img);
            addView(imgRed,Helper.toDp(16),Helper.toDp(16) );
        }

        img=getGreenStar(user);
        if(img!=null)
        {
            ImageView imgGreen=new ImageView(getContext());
            imgGreen.setImageDrawable(img);
            addView(imgGreen,Helper.toDp(16),Helper.toDp(16));
        }

        img=getBlueStar(user);
        if(img!=null)
        {
            ImageView imgBlue=new ImageView(getContext());
            imgBlue.setImageDrawable(img);
            addView(imgBlue,Helper.toDp(16),Helper.toDp(16));
        }

    }

    public Drawable getGreenStar(UserSearchDTO user)
    {
        AchievementsModel.AchievementStar star = AchievementsModel.Achievements.GetGreenStar(user);
        if(star.equals(AchievementsModel.AchievementStar.Gold))
        {
            return getResources().getDrawable(R.drawable.green_gold_star);
        }
        if(star.equals(AchievementsModel.AchievementStar.Silver))
        {
            return getResources().getDrawable(R.drawable.green_silver_star);
        }
        if(star.equals(AchievementsModel.AchievementStar.Bronze))
        {
            return getResources().getDrawable(R.drawable.green_brown_star);
        }
        return null;
    }

    public Drawable getBlueStar(UserSearchDTO user)
    {
        AchievementsModel.AchievementStar star = AchievementsModel.Achievements.GetBlueStar(user);
        if(star.equals(AchievementsModel.AchievementStar.Gold))
        {
            return getResources().getDrawable(R.drawable.blue_gold_star);
        }
        if(star.equals(AchievementsModel.AchievementStar.Silver))
        {
            return getResources().getDrawable(R.drawable.blue_silver_star);
        }
        if(star.equals(AchievementsModel.AchievementStar.Bronze))
        {
            return getResources().getDrawable(R.drawable.blue_brown_star);
        }
        return null;
    }

    public Drawable getRedStar(UserSearchDTO user)
    {
        AchievementsModel.AchievementStar star = AchievementsModel.Achievements.GetRedStar(user);
        if(star.equals(AchievementsModel.AchievementStar.Gold))
        {
            return getResources().getDrawable(R.drawable.red_gold_star);
        }
        if(star.equals(AchievementsModel.AchievementStar.Silver))
        {
            return getResources().getDrawable(R.drawable.red_silver_star);
        }
        if(star.equals(AchievementsModel.AchievementStar.Bronze))
        {
            return getResources().getDrawable(R.drawable.red_brown_star);
        }
        return null;
    }
}
