package muskanclasses.class12th.modelpaper.science;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.Manifest;
import android.os.Bundle;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ListActivity extends AppCompatActivity {

    WebView webView;
    SwipeRefreshLayout swipeRefreshLayout;
    private ScreenCaptureHelper screenCaptureHelper;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private ValueCallback<Uri[]> filePathCallback;


    String url = "false";
    String loginWithGoogle = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });





        swipeRefreshLayout = findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });


        webView = findViewById(R.id.webview);
        webView.setOnLongClickListener(v -> true);
        webView.setLongClickable(false);

// Enable essential settings
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.setHapticFeedbackEnabled(false);

// Enable cookies
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true); // For API < 21
        cookieManager.setAcceptThirdPartyCookies(webView, true);
        webView.addJavascriptInterface(new web_function(ListActivity.this), "android");
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "null");
        String email = sharedPreferences.getString("email", "null");
        screenCaptureHelper = new ScreenCaptureHelper(this);
        if (sharedPreferences.getString("email", "null").equals("null")){

            Intent intent = new Intent(getApplicationContext(), LoginnActivity.class);
            startActivity(intent);

        }


        //String muskan = getIntent().getStringExtra("url"+"?name="+name+"&email="+email);
        //Toast.makeText(this, muskan, Toast.LENGTH_LONG).show();
        webView.loadUrl(getIntent().getStringExtra("url")+"?name="+name+"&email="+email);
        cookieManager.flush();


        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {

                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(ListActivity.this, url, Toast.LENGTH_LONG).show();
                super.onPageFinished(view, url);


            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                //Toast.makeText(ListActivity.this, url, Toast.LENGTH_SHORT).show();
                super.onPageStarted(view, url, favicon);
            }
        });


        webView.setWebChromeClient(new WebChromeClient(){


            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {




                if (consoleMessage.message().equals("loginWithGoogle")){

                    Intent intent = new Intent(getApplicationContext(), LoginnActivity.class);


                    loginWithGoogle = "false";
                    startActivity(intent);
                    finish();


                }
                if (consoleMessage.message().equals("screenshare")){

                    Toast.makeText(ListActivity.this, consoleMessage.message(), Toast.LENGTH_SHORT).show();
                    //screenCaptureHelper.startScreenCapture();

                    if (ContextCompat.checkSelfPermission(ListActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // Permission already granted, you can proceed with file operations
                        Toast.makeText(ListActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                        screenCaptureHelper.startScreenCapture();
                        
                    } else {
                        // Permission not granted, request permission
                        ActivityCompat.requestPermissions(ListActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
                        Toast.makeText(ListActivity.this, "sumanannananananannaa", Toast.LENGTH_SHORT).show();
                        
                    }



                }






                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }
        });






    }

    public class WebAppInterface {
        @android.webkit.JavascriptInterface
        public void receiveMessage(String msg) {
            // Show message from web in Toast
            Toast.makeText(ListActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){

            webView.goBack();
        } else {

            super.onBackPressed();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        screenCaptureHelper.onActivityResult(requestCode, resultCode, data);
    }


}