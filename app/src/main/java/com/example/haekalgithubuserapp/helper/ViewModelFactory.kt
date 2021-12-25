package com.example.haekalgithubuserapp.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.haekalgithubuserapp.ui.detailuser.UserDetailViewModel
import com.example.haekalgithubuserapp.ui.favoriteuser.ListFavoriteUserViewModel
import com.example.haekalgithubuserapp.ui.home.HomeViewModel

/**
 * View Model Factory for construct Application to target class.
 */
class ViewModelFactory(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListFavoriteUserViewModel::class.java)) {
            return ListFavoriteUserViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)){
            return UserDetailViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class: ${modelClass.name}")
    }
}