package com.example.haekalgithubuserapp.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.haekalgithubuserapp.ui.settings.SettingsPreferences
import com.example.haekalgithubuserapp.ui.settings.SettingsViewModel

/**
 * View Model Factory for construct SettingPreferences to SettingViewModel.
 */
class SettingsViewModelFactory(private val pref: SettingsPreferences) : NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}