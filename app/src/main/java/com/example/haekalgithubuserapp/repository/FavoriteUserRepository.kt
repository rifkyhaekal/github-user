package com.example.haekalgithubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.haekalgithubuserapp.database.FavoriteUser
import com.example.haekalgithubuserapp.database.FavoriteUserDao
import com.example.haekalgithubuserapp.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Class that become connector between viewmodel class and Dao classes.
 */
class FavoriteUserRepository(application: Application) {
    private val mFavoritUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoritUserDao = db.favoriteUserDao()
    }

    fun getUsername(userName: String): LiveData<String> = mFavoritUserDao.getUsername(userName)

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoritUserDao.getALlFavoriteUser()

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoritUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoritUserDao.delete(favoriteUser) }
    }
}