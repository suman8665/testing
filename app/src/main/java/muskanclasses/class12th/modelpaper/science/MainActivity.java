package muskanclasses.class12th.modelpaper.science;

import static android.view.View.GONE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView;

    Toolbar toolbar;
    NavigationView navigationView;

    ActionBarDrawerToggle actionBarDrawerToggle;
    boolean doubleBackToExitPressedOnce = false;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private BottomNavigationView bottomNavigationView;

    String url = "false";
    String loginWithGoogle = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "null");
        String email = sharedPreferences.getString("email", "null");
        if (sharedPreferences.getString("email", "null").equals("null")){

            Intent intent = new Intent(getApplicationContext(), LoginnActivity.class);
            startActivity(intent);

        }

        AdsManager.init(this);
        AdsManager.loadInterstitialAd(this, new AdsManager.AdEventListener() {
            @Override
            public void onAdLoaded(String type) {
                Log.d("Ad", "Interstitial Loaded");
            }

            @Override
            public void onAdFailed(String type, String error) {
                Log.e("Ad", "Interstitial Failed: " + error);
            }

            @Override
            public void onAdClosed(String type) {
                Log.d("Ad", "Interstitial Closed");
            }

            @Override
            public void onRewardEarned(RewardItem reward) {
                // Not applicable
            }
        });






        sidemenu();


        webView = findViewById(R.id.webview);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);

// Disable long press (copy/paste block)
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

// Add JS Interface
        webView.addJavascriptInterface(new web_function(MainActivity.this), "android");

// Load your initial URL
        webView.loadUrl("https://muskanclasses.com/application/12th-science/poll_quize/dashboard.php?email="+email+"&name="+name);

// ✅ Flush cookies to make them persistent
        cookieManager.flush();



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                webView.reload();
            }
        });

        swipeRefreshLayout.setRefreshing(true);


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, url, Toast.LENGTH_LONG).show();
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                webView.setVisibility(GONE);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

                Toast.makeText(MainActivity.this, consoleMessage.message(), Toast.LENGTH_SHORT).show();



                if (consoleMessage.message().equals("loginWithGoogle")){

                    Intent intent = new Intent(getApplicationContext(), LoginnActivity.class);


                    loginWithGoogle = "false";
                    startActivity(intent);
                    finish();


                }




                return super.onConsoleMessage(consoleMessage);
            }

        });


        //webView.loadUrl("https://muskanclasses.com/application/12th-science/layout/model-paper/home.php?v=2");



       /* Intent intent = new Intent(getApplicationContext(), LoginnActivity.class);
        startActivity(intent);



        */



    }


    private void sidemenu() {

        toolbar = findViewById(R.id.webtoolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_menu);
        navigationView.setItemIconTintList(null);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int menuitemid = menuItem.getItemId();

                if (menuitemid==R.id.share){

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);

                }


                if (menuitemid==R.id.rate){

                    Intent rateus = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                    startActivity(rateus);

                }


                if (menuitemid==R.id.email){



                }















                layouthide();



                return true;

            }


        });


    }

    private void layouthide() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);

        } else {




            if (webView.canGoBack()) {

                webView.goBack();

            } else {

                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 1000);
            }
        }

    }

    @Override
    public void onBackPressed() {

        layouthide();

    }
}