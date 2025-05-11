package muskanclasses.class12th.modelpaper.science;

import android.app.Application;

import com.onesignal.OneSignal;

public class suman extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        // OneSignal ko initialize karte hain
        OneSignal.initWithContext(this);
        OneSignal.setAppId("YOUR_ONESIGNAL_APP_ID");  // Apni OneSignal App ID yahan dalen
    }
}
