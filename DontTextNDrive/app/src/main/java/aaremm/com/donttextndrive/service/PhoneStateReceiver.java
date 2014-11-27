package aaremm.com.donttextndrive.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import aaremm.com.donttextndrive.config.BApp;

/**
 * Created by rahul on 07-08-2014.
 */
public class PhoneStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MyPhoneStateListener phoneListener = new MyPhoneStateListener();
        TelephonyManager telephony = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public class MyPhoneStateListener extends PhoneStateListener {
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                  //  Log.d("DEBUG", "IDLE");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    /*if (System.currentTimeMillis() == BootstrapApplication.calendar.getTimeInMillis() - 4 * 1000) {
                        endCall();
                        Log.d("DEBUG", "OFFHOOK");
                    }*/
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                     //   Log.d("DEBUG", "RINGING");
                    if(!BApp.getInstance().getSPBoolean("mode")&&BApp.getInstance().getSPBoolean("switch")&&BApp.getInstance().isDrivingOrWalking()){
                        BApp.getInstance().waitAndReply("call",incomingNumber);
                    }
                    break;
            }
        }
    }
}
