package com.example.haekalgithubuserapp.ui.detailuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haekalgithubuserapp.*
import com.example.haekalgithubuserapp.api.ApiConfig
import com.example.haekalgithubuserapp.database.FavoriteUser
import com.example.haekalgithubuserapp.model.FollowResponseItem
import com.example.haekalgithubuserapp.model.MainInfoData
import com.example.haekalgithubuserapp.model.UserDetailResponse
import com.example.haekalgithubuserapp.repository.FavoriteUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application): ViewModel() {
    private val app = application

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _userDetail = MutableLiveData<UserDetailResponse?>()
    val userDetail: LiveData<UserDetailResponse?> = _userDetail

    private val _followerList = MutableLiveData<List<FollowResponseItem>?>()
    val followerList: LiveData<List<FollowResponseItem>?> = _followerList

    private val _followingList = MutableLiveData<List<FollowResponseItem>?>()
    val followingList: LiveData<List<FollowResponseItem>?> = _followingList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showMainInfo = MutableLiveData(MainInfoData(false, null, null))
    val showMainInfo: LiveData<MainInfoData> = _showMainInfo

    private val _showDetailUser = MutableLiveData<Boolean>()
    val showDetailUser: LiveData<Boolean> = _showDetailUser

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun getUsername(userName: String): LiveData<String> = mFavoriteUserRepository.getUsername(userName)

    /**
     * Function that used for requesting detail user end point.
     *
     * @param userId is string data type that contain username that have clicked in the search menu.
     */
    fun getDetailUser(userId: String) {
        _isLoading.value = true
        _showDetailUser.value = false
        val client = ApiConfig.getApiService().getDetailUser(userId)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                _showMainInfo.value = _showMainInfo.value?.copy(false, null, null)
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _showDetailUser.value = true
                        _userDetail.value = responseBody
                        _showMainInfo.value = _showMainInfo.value?.copy(false, null, null)
                    }
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                _showMainInfo.value = _showMainInfo.value?.copy(true, app.resources.getString(R.string.no_connection), R.drawable.no_connection)
            }

        })
    }

    /**
     * Function that show follower list from selected user
     *
     * @param userName is string data type that contain username of the selected user.
     */
    fun showFollowerList(userName: String) {
        val client = ApiConfig.getApiService().getUserFollowers(userName)
        client.enqueue(object : Callback<List<FollowResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                if ((response.isSuccessful) && response.body() != null) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.isNotEmpty()) {
                        _followerList.value = responseBody
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                Log.e(this.toString(), "onFailure: ${t.message}")
            }

        })
    }

    /**
     * Function that show following list from selected user
     *
     * @param userName is string data type that contain username of the selected user.
     */
    fun showFollowingList(userName: String) {
        val client = ApiConfig.getApiService().getUserFollowing(userName)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                if ((response.isSuccessful) && response.body() != null) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followingList.value = responseBody
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                Log.e(this.toString(), "onFailure: ${t.message}")
            }

        })
    }
}