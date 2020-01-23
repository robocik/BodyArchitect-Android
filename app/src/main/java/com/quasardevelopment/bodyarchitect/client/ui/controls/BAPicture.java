package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.PictureInfoDTO;


public class BAPicture extends ImageView
{
    public BAPicture(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setImageDrawable(getResources().getDrawable(R.drawable.default_profile));

    }

    public void fill(PictureInfoDTO info)
    {
        if(info==null || info.pictureId==null)
        {
            setImageDrawable(getResources().getDrawable(R.drawable.default_profile));
            return;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.default_profile)
                .showImageForEmptyUri(R.drawable.default_profile)
                .showImageOnFail(R.drawable.default_profile)
                .cacheInMemory()
                .cacheOnDisc()
                .displayer(new FadeInBitmapDisplayer(200))
                .build();
        ImageLoader.getInstance().displayImage(info.pictureId.toString(),this, options);
    }

}
