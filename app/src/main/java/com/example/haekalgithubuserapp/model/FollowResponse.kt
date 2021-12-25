package com.example.haekalgithubuserapp.model

import com.google.gson.annotations.SerializedName

/**
 * Data class used in Follower and Following RecyclerList
 */
data class FollowResponse(

	@field:SerializedName("FollowResponse")
	val followResponse: List<FollowResponseItem>
)

data class FollowResponseItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("login")
	val login: String
)
