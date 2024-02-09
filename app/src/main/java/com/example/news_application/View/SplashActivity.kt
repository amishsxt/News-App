package com.example.news_application.View

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.news_application.R
import com.example.news_application.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var xml: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //root
        xml = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(xml.root)

        // Fade-in animation
        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fadeInAnimation.duration = 1000

        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                if (isConnectedToInternet()) {
                    startNav()
                } else {
                    showNoInternetDialog()
                }
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })

        xml.splashLayout.startAnimation(fadeInAnimation)
    }

    private fun startNav() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities =
            connectivityManager.getNetworkCapabilities(network)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }

    private fun showNoInternetDialog() {
        val dialogFragment = NoInternetDialogFragment {
            if (isConnectedToInternet()) {
                startNav()
            } else {
                showNoInternetDialog()
            }
        }
        dialogFragment.show(supportFragmentManager, "NoInternetDialog")
    }
}