package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.UpgradeAccountFragment;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 19.07.13
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public class AdControl extends LinearLayout {
    AdView adView;
    ImageButton btnRemoveAds;

    public AdControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.ad_control, this);
        adView= (AdView) findViewById(R.id.adView);

        btnRemoveAds= (ImageButton) findViewById(R.id.btnRemoveAds);
        btnRemoveAds.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                UpgradeAccountFragment.EnsureAccountType(AdControl.this.getContext());
            }
        });

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode)
            {
                super.onAdFailedToLoad(errorCode);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
            {

            }
                    btnRemoveAds.setVisibility(adView.isShown()?View.VISIBLE:View.GONE);
            }
        });
        btnRemoveAds.setVisibility(adView.isShown()?View.VISIBLE:View.GONE);
        EnsureVisible();

    }
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        EnsureVisible();
        super.onWindowVisibilityChanged(visibility);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public boolean EnsureVisible()
    {
        boolean showAd=ApplicationState.getCurrent()!=null && !ApplicationState.getCurrent().isPremium();
        if(showAd)
        {

            if(!adView.isShown( ) && !adView.isLoading())
            {
                AdRequest.Builder builder = new AdRequest.Builder();
                if(Constants.IsDebugMode)
                {
                    builder.addTestDevice("66084805792e162f");
                    builder.addTestDevice("0C7C0C3D3ABFAF6ED49A51CB6FF3678E");
                    builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
                }
                AdRequest req=builder.build();
                adView.loadAd(req);

            }
            setVisibility(View.VISIBLE);
        }
        else
        {
            setVisibility(View.GONE);
        }
        return showAd;
    }
}
