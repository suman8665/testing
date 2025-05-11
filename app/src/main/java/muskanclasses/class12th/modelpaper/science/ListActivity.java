package muskanclasses.class12th.modelpaper.science;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ListActivity extends AppCompatActivity {

    WebView webView;
    SwipeRefreshLayout swipeRefreshLayout;


    String url = "false";

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
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHapticFeedbackEnabled(false);
        webView.addJavascriptInterface(new web_function(ListActivity.this), "android");

        webView.loadUrl(getIntent().getStringExtra("url"));


        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {

                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }
        });


        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {






                return super.onConsoleMessage(consoleMessage);
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
}