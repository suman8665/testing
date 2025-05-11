package muskanclasses.class12th.modelpaper.science;

package muskanclasses.class12th.modelpaper.science;

import android.net.Uri;
import androidx.core.app.NotificationCompat;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OSNotificationReceivedResult;

public class MyNotificationService extends NotificationExtenderService {

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedEvent receivedEvent) {

        Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification_sound);

        receivedEvent.getNotification().setExtender(builder ->
                builder.setSound(soundUri)
                        .setSmallIcon(R.drawable.custom_icon)
                        .setContentTitle("Custom Title")
                        .setContentText("Custom Message"));

        return true; // Stop default notification

    }
}
