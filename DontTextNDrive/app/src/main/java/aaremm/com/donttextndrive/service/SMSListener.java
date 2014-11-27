package aaremm.com.donttextndrive.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import aaremm.com.donttextndrive.config.BApp;

/**
 * Created by rahul on 25-11-2014.
 */
public class SMSListener extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    if(!BApp.getInstance().getSPBoolean("mode") &&BApp.getInstance().getSPBoolean("switch")&& BApp.getInstance().isDrivingOrWalking()){
                        BApp.getInstance().waitAndReply("sms",phoneNumber);
                    }
                   // Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
}