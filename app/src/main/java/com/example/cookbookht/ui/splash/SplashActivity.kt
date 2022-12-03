package com.example.cookbookht.ui.splash

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.cookbookht.R
import com.example.cookbookht.sharePreference.Preferences
import com.example.cookbookht.ui.main.MainActivity
import com.example.cookbookht.ui.slide.SlideActivity
import org.koin.android.ext.android.inject
import java.util.*

class SplashActivity : AppCompatActivity() {

    private val prefs : Preferences by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        if (prefs.isFistTime.get()){
            prefs.isLanguageVi.set("en")
            prefs.isFistTime.set(false)
        }

        if (prefs.isLanguageVi.get() == "vi"){
            changeLanguage("vi")
        }else{
            changeLanguage("en")
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (prefs.isLoginSuccess.get()){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, SlideActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 4500)
    }

    private fun changeLanguage(language: String) {
        val locale = Locale(language)
        val config = Configuration()
        config.locale = locale

        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )
    }
}