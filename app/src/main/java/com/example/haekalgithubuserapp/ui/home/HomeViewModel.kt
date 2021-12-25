package com.example.haekalgithubuserapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haekalgithubuserapp.*
import com.example.haekalgithubuserapp.api.ApiConfig
import com.example.haekalgithubuserapp.model.ItemsItem
import com.example.haekalgithubuserapp.model.MainInfoData
import com.example.haekalgithubuserapp.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application): ViewModel() {
    private val app = application

    private val _searchUser = MutableLiveData<List<ItemsItem>>()
    val searchUser: LiveData<List<ItemsItem>> = _searchUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showSearchUser = MutableLiveData<Boolean>()
    val showSearchUser: LiveData<Boolean> = _showSearchUser

    private val _showMainInfo = MutableLiveData(MainInfoData(true, app.resources.getString(R.string.welcome), R.drawable.welcome))
    val showMainInfo: LiveData<MainInfoData> get() = _showMainInfo

    /**
     * Function that used for requesting search user end point.
     *
     * @param query is string data type that contain wanted username.
     */
    fun searchGithubUser(query: String) {
        _isLoading.value = true
        _showSearchUser.value = false

        val client = ApiConfig.getApiService().getSearchUser(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                _showMainInfo.value = _showMainInfo.value?.copy(false, null, null)
                if (response.isSuccessful && response.body()?.totalCount != 0) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _showSearchUser.value = true
                        _searchUser.value = responseBody.items
                        _showMainInfo.value = _showMainInfo.value?.copy(false, null, null)
                    }
                } else {
                    _showSearchUser.value = false
                    _showMainInfo.value = _showMainInfo.value?.copy(true, app.resources.getString(R.string.no_result_found), R.drawable.no_results)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                _showMainInfo.value = _showMainInfo.value?.copy(true, app.resources.getString(R.string.no_connection), R.drawable.no_connection)
            }
        })
    }
}