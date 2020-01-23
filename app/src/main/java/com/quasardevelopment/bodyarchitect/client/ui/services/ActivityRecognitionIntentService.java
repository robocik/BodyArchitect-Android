package com.quasardevelopment.bodyarchitect.client.ui.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.quasardevelopment.bodyarchitect.client.util.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 17.07.13
 * Time: 07:32
 * To change this template use File | Settings | File Templates.
 */
public class ActivityRecognitionIntentService extends IntentService {

    public ActivityRecognitionIntentService(String name) {
        super(name);
    }

    public ActivityRecognitionIntentService() {
        super("BAActivityRecognition");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // If the incoming intent contains an update
        if (ActivityRecognitionResult.hasResult(intent)) {
            // Get the update
            ActivityRecognitionResult result =
                    ActivityRecognitionResult.extractResult(intent);
            // Get the most probable activity
            DetectedActivity mostProbableActivity =
                    result.getMostProbableActivity();

            /*
             * Get the probability that this activity is the
             * the user's actual activity
             */
            int confidence = mostProbableActivity.getConfidence();

            Intent msgIntent = new Intent(Constants.AutoPauseFilter);
            boolean isAutoPause=mostProbableActivity.getType()==DetectedActivity.STILL && confidence>60;
            // Add data
            msgIntent.putExtra("IsAutoPause",isAutoPause);
            LocalBroadcastManager.getInstance(this).sendBroadcast(msgIntent);

        } else {
            /*
             * This implementation ignores intents that don't contain
             * an activity update. If you wish, you can report them as
             * errors.
             */
        }
    }
}
