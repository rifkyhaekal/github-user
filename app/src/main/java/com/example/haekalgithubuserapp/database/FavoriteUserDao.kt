package com.example.haekalgithubuserapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Interface that control query of database
 *
 */
@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT username FROM favoriteuser WHERE username = :userName")
    fun getUsername(userName: String): LiveData<String>

    @Query("SELECT * FROM favoriteUser ORDER BY username ASC")
    fun getALlFavoriteUser(): LiveData<List<FavoriteUser>>
}