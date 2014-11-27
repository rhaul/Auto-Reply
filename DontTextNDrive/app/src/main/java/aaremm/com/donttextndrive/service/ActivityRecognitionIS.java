package aaremm.com.donttextndrive.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import aaremm.com.donttextndrive.config.BApp;


/**
 * Created by rahul on 02-11-2014.
 */
public class ActivityRecognitionIS extends IntentService {

    public ActivityRecognitionIS() {
        super("ActivityRecognitionIS");
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
            /*
             * Get an integer describing the type of activity
             */
            int activityType = mostProbableActivity.getType();
            doSomethingFromType(activityType);
        }
    }

    /**
     * Map detected activity types to strings
     *
     * @param activityType The detected activity type
     * @return A user-readable name for the type
     */
    private void doSomethingFromType(int activityType) {
        String type = "";
        switch (activityType) {
            case DetectedActivity.IN_VEHICLE:
                BApp.getInstance().setDrivingOrWalking(true);
                type = "driving";
                Log.d("Activity", "Vehicle");
                break;
            case DetectedActivity.ON_BICYCLE:
                BApp.getInstance().setDrivingOrWalking(true);
                type = "bicycling";
                Log.d("Activity", "Bic");
                break;
            case DetectedActivity.ON_FOOT:
                if(BApp.getInstance().getSPBoolean("walk")) {
                    BApp.getInstance().setDrivingOrWalking(true);
                }
                type = "walking";
                Log.d("Activity", "Foot");
                break;
            case DetectedActivity.STILL:
                BApp.getInstance().setDrivingOrWalking(false);
                type = "standing";
                Log.d("Activity", "Still");
                //do nothing
                break;
            case DetectedActivity.UNKNOWN:
                // do nothing
                break;
            case DetectedActivity.TILTING:
                //do nothing
                break;
        }
        showToast(type);
    }

    private void showToast(final String type) {
        if (!BApp.getInstance().getPrevType().equalsIgnoreCase(type)) {
            BApp.getInstance().setPrevType(type);
            Handler mHandler = new Handler(getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BApp.getInstance(), "You're " + BApp.getInstance().getPrevType(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
