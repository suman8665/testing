package muskanclasses.class12th.modelpaper.science;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog;

public class ShareDialog {

    private Context context;

    public ShareDialog(Context context) {
        this.context = context;
    }

    // Method to show the custom share dialog
    public void show() {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.custom_share_dialog, null);

        // Create the dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        // Create the dialog instance
        AlertDialog dialog = builder.create();

        // Find the share buttons and set click listeners
        ImageButton whatsappButton = dialogView.findViewById(R.id.share_whatsapp);
        whatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareOnWhatsApp();
                dialog.dismiss();
            }
        });

        ImageButton facebookButton = dialogView.findViewById(R.id.share_facebook);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareOnFacebook();
                dialog.dismiss();
            }
        });

        ImageButton emailButton = dialogView.findViewById(R.id.share_email);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareViaEmail();
                dialog.dismiss();
            }
        });

        ImageButton linkButton = dialogView.findViewById(R.id.share_link);
        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareViaLink();
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }

    // Share on WhatsApp
    private void shareOnWhatsApp() {
        String message = "Check out this amazing content!";
        Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
        whatsappIntent.setData(Uri.parse("https://wa.me/yourwhatsappnumber?text=" + message));
        context.startActivity(whatsappIntent);
    }

    // Share on Facebook
    private void shareOnFacebook() {
        String message = "Check out this amazing content!";
        Intent facebookIntent = new Intent(Intent.ACTION_SEND);
        facebookIntent.setType("text/plain");
        facebookIntent.putExtra(Intent.EXTRA_TEXT, message);
        facebookIntent.setPackage("com.facebook.katana"); // Package for Facebook app
        context.startActivity(facebookIntent);
    }

    // Share via Email
    private void shareViaEmail() {
        String subject = "Check out this content!";
        String body = "Here's some amazing content I wanted to share with you!";
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

    // Share via Link (custom URL)
    private void shareViaLink() {
        String link = "https://www.example.com";
        Intent linkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        context.startActivity(linkIntent);
    }
}
