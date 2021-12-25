package com.example.haekalgithubuserapp.api

import com.example.haekalgithubuserapp.model.FollowResponseItem
import com.example.haekalgithubuserapp.model.SearchResponse
import com.example.haekalgithubuserapp.model.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * An interface used for defin the endpoint that used by application
 */
interface ApiService {
    @Headers("Authorization: <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>")
    @GET("search/users")
    fun getSearchUser(
        @Query("q") q: String
    ): Call<SearchResponse>

    @Headers("Authorization: <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>")
    @GET("users/{id}")
    fun getDetailUser(
        @Path("id") id: String
    ): Call<UserDetailResponse>

    @Headers("Authorization: <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>")
    @GET("users/{id}/followers")
    fun getUserFollowers(
        @Path("id") id: String
    ): Call<List<FollowResponseItem>>

    @Headers("Authorization: <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>")
    @GET("users/{id}/following")
    fun getUserFollowing(
        @Path("id") id: String
    ): Call<List<FollowResponseItem>>
}