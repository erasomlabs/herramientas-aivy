package erasom.herramientas.aivy

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

/**
 * Gestor de monetización de herramientas.aivy.
 * Unidades de anuncio (AdMob):
 *  - Intersticial:            ca-app-pub-2946370347896607/591404887
 *  - Bonificado (rewarded):   ca-app-pub-2946370347896607/1251699383
 *  - Banner:                  ca-app-pub-2946370347896607/5689862568
 *  - Intersticial bonificado: ca-app-pub-2946370347896607/3555588197
 */
class MonetizationManager(private val activity: Activity) {

    companion object {
        private const val TAG = "AivyMonetization"
        private const val BANNER_ID = "ca-app-pub-2946370347896607/5689862568"
        private const val REWARDED_ID = "ca-app-pub-2946370347896607/1251699383"
        private const val REWARDED_INTERSTITIAL_ID = "ca-app-pub-2946370347896607/3555588197"
        private const val INTERSTITIAL_ID = "ca-app-pub-2946370347896607/591404887"
    }

    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null
    private var adView: AdView? = null
    private var consentInformation: ConsentInformation? = null
    private var toolUsageCount = 0

    fun initPrivacyAndAds(onReady: () -> Unit) {
        val params = ConsentRequestParameters.Builder().build()
        consentInformation = UserMessagingPlatform.getConsentInformation(activity)
        consentInformation?.requestConsentInfoUpdate(
            activity,
            params,
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { formError ->
                    if (formError != null) Log.w(TAG, "Consent error: ${formError.message}")
                    if (consentInformation?.canRequestAds() == true) initializeMobileAds(onReady) else onReady()
                }
            },
            { error ->
                Log.w(TAG, "Consent request error: ${error.message}")
                initializeMobileAds(onReady)
            }
        )
    }

    private fun initializeMobileAds(onReady: () -> Unit) {
        MobileAds.initialize(activity) {
            loadInterstitial()
            loadRewarded()
            onReady()
        }
    }

    fun attachBanner(container: ViewGroup) {
        if (adView != null) return
        val ad = AdView(activity)
        ad.setAdSize(AdSize.BANNER)
        ad.adUnitId = BANNER_ID
        adView = ad
        container.removeAllViews()
        container.addView(ad)
        ad.loadAd(AdRequest.Builder().build())
    }

    fun hideBanner() {
        adView?.visibility = android.view.View.GONE
    }

    fun showBanner() {
        adView?.visibility = android.view.View.VISIBLE
    }

    private fun loadInterstitial() {
        InterstitialAd.load(
            activity,
            INTERSTITIAL_ID,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }

    /** Se muestra intersticial cada 4 herramientas usadas. */
    fun onToolClosed(onComplete: () -> Unit) {
        toolUsageCount++
        val ad = interstitialAd
        if (toolUsageCount % 4 == 0 && ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    loadInterstitial()
                    onComplete()
                }
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    loadInterstitial()
                    onComplete()
                }
            }
            ad.show(activity)
            interstitialAd = null
        } else {
            onComplete()
        }
    }

    private fun loadRewarded() {
        RewardedAd.load(
            activity,
            REWARDED_ID,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    rewardedAd = null
                }
            }
        )
    }

    /** Anuncio bonificado para desbloquear funciones premium temporales (p. ej. exportar sin marca de agua). */
    fun showRewardedUnlock(onRewarded: () -> Unit, onFailedOrDismissed: () -> Unit) {
        val currentAd = rewardedAd
        if (currentAd != null) {
            var rewardEarned = false
            currentAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    loadRewarded()
                    if (rewardEarned) onRewarded() else onFailedOrDismissed()
                }
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    loadRewarded()
                    onFailedOrDismissed()
                }
            }
            currentAd.show(activity) {
                rewardEarned = true
            }
            rewardedAd = null
        } else {
            loadRewarded()
            onFailedOrDismissed()
        }
    }
}
