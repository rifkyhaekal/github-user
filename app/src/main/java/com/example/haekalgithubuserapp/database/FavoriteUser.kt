package com.example.haekalgithubuserapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class for Room database
 *
 * @param username is username (login) of user
 * @param htmlUrl is the url of user profile
 * @param avatarUrl is the url of avatar profile
 */
@Entity
class FavoriteUser(
    @PrimaryKey
    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "html_url")
    var htmlUrl: String? = null,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,
)
