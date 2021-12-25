package com.example.haekalgithubuserapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.haekalgithubuserapp.database.FavoriteUser

/**
 * Class that has a function to find out if there is a change to the recyclerview data.
 */
class FavoriteUserDiffCallback(private val mOldFavoriteUser: List<FavoriteUser>, private val mNewFavoriteUser: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteUser.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteUser.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteUser[oldItemPosition].username == mNewFavoriteUser[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavoriteUser[oldItemPosition]
        val newEmployee = mNewFavoriteUser[newItemPosition]
        return oldEmployee.username == newEmployee.username && oldEmployee.htmlUrl == newEmployee.htmlUrl && oldEmployee.avatarUrl == newEmployee.avatarUrl
    }
}