package muskanclasses.class12th.modelpaper.science;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenCaptureHelper {

    private Activity activity;
    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mediaProjection;

    public ScreenCaptureHelper(Activity activity) {
        this.activity = activity;
        mediaProjectionManager = (MediaProjectionManager) activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    // Method to start the screen capture process
    public void startScreenCapture() {
        Intent captureIntent = mediaProjectionManager.createScreenCaptureIntent();
        activity.startActivityForResult(captureIntent, 100);
    }

    // This method will handle the result of the screen capture permission
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
                // Capture the screen after permission is granted
                captureScreen();
            } else {
                Toast.makeText(activity, "Screen Capture Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Capture the screen
    private void captureScreen() {
        // Here, you can implement VirtualDisplay and use MediaProjection to start capturing
        // For now, I'm just showing how you'd get the screen size and display metrics
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        // Now you can use VirtualDisplay to capture the screen, process it as required
        // This is just an example of getting the screen resolution
        Bitmap screenshot = captureScreenshot(width, height);
        shareImage(screenshot);
    }

    // Method to capture screenshot (this part is hypothetical, as actual implementation
    // would depend on using a VirtualDisplay and Surface)
    private Bitmap captureScreenshot(int width, int height) {
        // Your actual screenshot capture logic here
        // For simplicity, returning a null Bitmap
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    // Method to display the screenshot in an ImageView or elsewhere


    private void shareImage(Bitmap screenshot) {
        // Save the screenshot to a file
        File screenshotFile = new File(activity.getExternalFilesDir(null), "screenshot.png");
        try (FileOutputStream out = new FileOutputStream(screenshotFile)) {
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create an intent to share the screenshot
        Uri screenshotUri = Uri.fromFile(screenshotFile);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");
        shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        activity.startActivity(Intent.createChooser(shareIntent, "Share Screenshot"));
    }
}
