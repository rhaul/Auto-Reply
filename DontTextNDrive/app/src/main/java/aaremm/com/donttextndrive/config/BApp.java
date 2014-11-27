package aaremm.com.donttextndrive.config;

import android.app.Application;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BApp extends Application{
    // app
    private static BApp instance;
    private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 20;
    private static Bitmap.CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    private static int DISK_IMAGECACHE_QUALITY = 80;  //PNG is lossless so quality is ignored but must be provided
    public static SharedPreferences sp;
    // Debugging tag for the application
    public static final String APPTAG = "donttextndrive";


    public AudioManager manager;
    /**
     * Create main application
     */
    public BApp() {

    }

    /**
     * Create main application
     *
     * @param context
     */
    public BApp(final Context context) {
        this();
        attachBaseContext(context);
    }


    public static void setSP(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getInstance());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static void setSPBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getInstance());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public static void setSPInteger(String key, int value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getInstance());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static Integer getSPInteger(String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getInstance());
        return sharedPreferences.getInt(key, 0); // 0 - professor 1 - student
    }

    public static Boolean getSPBoolean(String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getInstance());
        return sharedPreferences.getBoolean(key, false);
    }

    public String getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;

                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d("HashKey", something);
                return something;
            }
        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * Create main application
     *
     * @param instrumentation
     */
    public BApp(final Instrumentation instrumentation) {
        this();
        attachBaseContext(instrumentation.getTargetContext());
    }

    public static BApp getInstance() {

        if (instance == null) {
            instance = new BApp();
            return instance;

        } else {
            return instance;
        }
    }

    public void setSPLong(String key, long value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getInstance());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public boolean isDrivingOrWalking = false;
    public String smsTextHeader = "Auto-Reply: Thanks for your ";
    public static String smsTextBody = "Since I'm driving now so can't reply.I will get back to you soon.";


    public static String getSPString(String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getInstance());
        return sharedPreferences.getString(key,smsTextBody);
    }

    public static void setSPString(String key,String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getInstance());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getSmsTextBody() {
        return smsTextBody;
    }

    public void setSmsTextBody(String smsTextBody) {
        this.smsTextBody = smsTextBody;
    }

    public boolean isDrivingOrWalking() {
        return isDrivingOrWalking;
    }

    public void setDrivingOrWalking(boolean isDrivingOrWalking) {
        this.isDrivingOrWalking = isDrivingOrWalking;
    }

    public void waitAndReply(final String type, final String incomingNumber) { // type sms,call
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                sendSMS(type,incomingNumber);
            }
        }, 3000);
    }

    private void sendSMS(String type,String incomingNumber) {

        try {
            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(incomingNumber, null, smsTextHeader+type+". "+BApp.getInstance().getSPString("msg"), null, null);
            Toast.makeText(BApp.getInstance(), "Auto-reply sent to " + getContactName(incomingNumber),
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(BApp.getInstance(),
                    "Auto-reply failed!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    public String getContactName( String phoneNumber) {
        ContentResolver cr = BApp.getInstance().getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return phoneNumber;
        }
        String contactName = "";
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }

    public String prevType = "";

    public String getPrevType() {
        return prevType;
    }

    public void setPrevType(String prevType) {
        if(prevType.equalsIgnoreCase("")){
            prevType = "standing";
        }
        this.prevType = prevType;
    }
}


