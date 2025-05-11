package muskanclasses.class12th.modelpaper.science;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.webkit.JavascriptInterface;

public class web_function {

    private AppCompatActivity activity;

    // Constructor to pass the MainActivity context
    public web_function(AppCompatActivity activity) {
        this.activity = activity;
    }

    // Open Second Activity with URL
    @JavascriptInterface
    public void openSecondActivity(String url) {
        Intent intent = new Intent(activity, ListActivity.class);
        intent.putExtra("url", url);  // Pass the URL to SecondActivity
        activity.startActivity(intent);
    }

    // Open Chrome Custom Tab with URL
    @JavascriptInterface
    public void openChromeCustomTab(String url) {
        Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.arrow_back_24px);
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable).mutate();

            Bitmap bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888
            );

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            // Now set the close button icon
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(activity, R.color.app_color));
            builder.setCloseButtonIcon(bitmap); // your vector converted to bitmap
            builder.enableUrlBarHiding();
            //builder.setShowTitle(true);

            CustomTabsIntent customTabsIntent = builder.build();

            customTabsIntent.launchUrl(activity, Uri.parse(url));

        }
    }

    // Show Share Dialog
    @JavascriptInterface
    public void showShareDialog() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check this out!");
        activity.startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    // Show Email Dialog
    @JavascriptInterface
    public void showEmailDialog() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body text here");
        activity.startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

    // Open Telegram with URL
    @JavascriptInterface
    public void openTelegram(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
    }

    // Open WhatsApp with URL
    @JavascriptInterface
    public void openWhatsApp(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
    }

    // Open YouTube with URL
    @JavascriptInterface
    public void openYouTube(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
    }

    // Open Chrome with URL
    @JavascriptInterface
    public void openChrome(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
    }

    // Show Rate Us Dialog
    @JavascriptInterface
    public void showRateUsDialog() {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(goToMarket);
    }
}
