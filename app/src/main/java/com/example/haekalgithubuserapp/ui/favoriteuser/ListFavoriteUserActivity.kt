package com.example.haekalgithubuserapp.ui.favoriteuser

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haekalgithubuserapp.R
import com.example.haekalgithubuserapp.databinding.ActivityListFavoriteUserBinding
import com.example.haekalgithubuserapp.helper.ViewModelFactory

class ListFavoriteUserActivity : AppCompatActivity() {

    private var _acitivityListFavoriteUserBindding: ActivityListFavoriteUserBinding? = null
    private val binding get() = _acitivityListFavoriteUserBindding

    private lateinit var adapter: ListFavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _acitivityListFavoriteUserBindding = ActivityListFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = getString(R.string.favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listFavoriteUserViewModel = obtainViewModel(this@ListFavoriteUserActivity)
        listFavoriteUserViewModel.getAllFavoriteUser().observe(this, { favoriteList ->
            if (favoriteList != null) {
                adapter.setListFavoriteUse(favoriteList)
                if (favoriteList.isEmpty()) {
                    binding?.tvStatus?.visibility = View.VISIBLE
                    binding?.rvFavoriteUser?.visibility = View.INVISIBLE
                } else {
                    binding?.rvFavoriteUser?.visibility = View.VISIBLE
                    binding?.tvStatus?.visibility = View.INVISIBLE
                }
            }
        })

        adapter = ListFavoriteUserAdapter()

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.rvFavoriteUser?.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding?.rvFavoriteUser?.layoutManager = LinearLayoutManager(this)
        }
        binding?.rvFavoriteUser?.setHasFixedSize(true)
        binding?.rvFavoriteUser?.adapter = adapter
    }

    /**
     * A function that used for obtain view model.
     *
     * @param activity an activity context that call this function.
     */
    private fun obtainViewModel(activity: AppCompatActivity): ListFavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ListFavoriteUserViewModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _acitivityListFavoriteUserBindding = null
    }
}