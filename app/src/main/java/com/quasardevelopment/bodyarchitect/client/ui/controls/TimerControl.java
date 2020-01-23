package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 20.05.13
 * Time: 13:13
 * To change this template use File | Settings | File Templates.
 */
public class TimerControl extends AutoResizeTextView {
    Timer timer;
    boolean isStarted;

    public TimerControl(Context context, AttributeSet attrs) {
        super(context, attrs);


        setVisibility(View.GONE);
        setTextSize(getResources().getDimension(R.dimen.extra_large_font));
        setEllipsize(TextUtils.TruncateAt.END);
        setSingleLine(true);
    }

    public boolean isStarted()
    {
        return isStarted;
    }

    public void setIsStarted(boolean value)
    {
        if(value!=isStarted)
        {
            isStarted=value;
            if(isStarted)
            {
                startTimer();
            }
            else
            {
                stopTimer();
            }
        }
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge();
        setVisibility(GONE);

    }

    void updateTime()
    {
        if(ApplicationState.getCurrent()==null)
        {
            return;
        }
        if (ApplicationState.getCurrent().timerStartTime == null)
        {
            setText(DateTimeHelper.fromSeconds(0));
        }
        else
        {
            Duration duration = new Duration(ApplicationState.getCurrent().timerStartTime, DateTime.now());
            int seconds=(int)duration.getStandardSeconds();
            setText(DateTimeHelper.fromSeconds(seconds));
        }

    }

    private void startTimer() {
        setVisibility(VISIBLE);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ((Activity) TimerControl.this.getContext()).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        updateTime();
                    }
                });
            }
        }, 0, 1000);
    }

}
