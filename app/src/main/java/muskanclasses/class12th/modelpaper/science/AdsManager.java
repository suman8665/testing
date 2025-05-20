package muskanclasses.class12th.modelpaper.science;



import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdsManager {

    public static final String BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
    public static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    public static final String REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";

    public interface AdEventListener {
        void onAdLoaded(String type);
        void onAdFailed(String type, String error);
        void onAdClosed(String type);
        void onRewardEarned(RewardItem reward);
    }

    private static InterstitialAd mInterstitialAd;
    private static RewardedAd mRewardedAd;

    private static final String TAG = "AdsManager";

    public static void init(Context context) {
        MobileAds.initialize(context, initializationStatus -> Log.d(TAG, "MobileAds initialized"));
    }

    public static void loadBannerAd(Context context, FrameLayout container, String bannerAdUnitId, AdEventListener listener) {
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(BANNER_AD_UNIT_ID);
        container.removeAllViews();
        container.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                listener.onAdLoaded("banner");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                listener.onAdFailed("banner", adError.getMessage());
            }

            @Override
            public void onAdClosed() {
                listener.onAdClosed("banner");
            }
        });

        adView.loadAd(adRequest);
    }

    public static void loadInterstitialAd(Context context, AdEventListener listener) {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, INTERSTITIAL_AD_UNIT_ID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        listener.onAdLoaded("interstitial");

                        mInterstitialAd.setFullScreenContentCallback(new com.google.android.gms.ads.FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                listener.onAdClosed("interstitial");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                                listener.onAdFailed("interstitial", adError.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        mInterstitialAd = null;
                        listener.onAdFailed("interstitial", adError.getMessage());
                    }
                });
    }

    public static void showInterstitialAd(Activity activity) {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        } else {
            Log.d(TAG, "Interstitial ad not ready");
        }
    }

    public static void loadRewardedAd(Context context, String rewardedAdUnitId, AdEventListener listener) {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, REWARDED_AD_UNIT_ID, adRequest,
                new RewardedAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        listener.onAdLoaded("rewarded");

                        mRewardedAd.setFullScreenContentCallback(new com.google.android.gms.ads.FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                listener.onAdClosed("rewarded");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                                listener.onAdFailed("rewarded", adError.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        mRewardedAd = null;
                        listener.onAdFailed("rewarded", adError.getMessage());
                    }
                });
    }

    public static void showRewardedAd(Activity activity, AdEventListener listener) {
        if (mRewardedAd != null) {
            mRewardedAd.show(activity, rewardItem -> {
                listener.onRewardEarned(rewardItem);
            });
        } else {
            Log.d(TAG, "Rewarded ad not ready");
        }
    }

    public static void showInterstitialAdWithCallback(Activity activity, Runnable onAdClosedCallback) {
        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new com.google.android.gms.ads.FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    mInterstitialAd = null;
                    if (onAdClosedCallback != null) onAdClosedCallback.run();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                    mInterstitialAd = null;
                    if (onAdClosedCallback != null) onAdClosedCallback.run();
                }
            });

            mInterstitialAd.show(activity);
        } else {
            Log.d(TAG, "Interstitial ad not ready");
            if (onAdClosedCallback != null) onAdClosedCallback.run();
        }
    }

}
