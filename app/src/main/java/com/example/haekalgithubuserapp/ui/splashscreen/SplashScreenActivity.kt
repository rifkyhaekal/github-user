package com.example.haekalgithubuserapp.ui.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.haekalgithubuserapp.ui.main.MainActivity
import com.example.haekalgithubuserapp.R
import com.example.haekalgithubuserapp.databinding.ActivitySplashScreenBinding
import com.example.haekalgithubuserapp.helper.SettingsViewModelFactory
import com.example.haekalgithubuserapp.ui.settings.SettingsPreferences
import com.example.haekalgithubuserapp.ui.settings.SettingsViewModel
import com.example.haekalgithubuserapp.ui.settings.dataStore

/**
 * Class for splash screen.
 *
 * This class just used for splash screen and checking the dark theme configuration.
 */
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)
        binding.splashScreen.startAnimation(sideAnimation)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME)

        val pref = SettingsPreferences.getInstance(dataStore)
        val settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref)).get(
            SettingsViewModel::class.java
        )

        settingsViewModel.getThemeSettings().observe(this,
            {   isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
    }

    /**
     * Static value.
     *
     * In this static value define the duration of splash screen.
     * @property SPLASH_TIME Long data type use for define the duration in milliseconds
     */
    companion object {
        const val SPLASH_TIME: Long = 3000
    }
}