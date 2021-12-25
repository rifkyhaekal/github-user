package com.example.haekalgithubuserapp.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.haekalgithubuserapp.R
import com.example.haekalgithubuserapp.databinding.FragmentHomeBinding
import com.example.haekalgithubuserapp.helper.ViewModelFactory
import com.example.haekalgithubuserapp.model.ItemsItem
import com.example.haekalgithubuserapp.model.MainInfoData

/**
 * Class that will display while running the application first.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.maxWidth = Int.MAX_VALUE

        homeViewModel = obtainViewModel(activity as AppCompatActivity)

        homeViewModel.searchUser.observe(this, { searchUser ->
            setSearchData(searchUser)
        })

        homeViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        homeViewModel.showSearchUser.observe(this, {
            showSearchUser(it)
        })

        homeViewModel.showMainInfo.observe(this, {
            showMainInfo(it)
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                homeViewModel.searchGithubUser(query.toString())
                showMainInfo(MainInfoData(false, null, null))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_user -> {
                val toFavoriteUserActivity =
                    HomeFragmentDirections.actionHomeFragmentToListFavoriteUserActivity()
                findNavController().navigate(toFavoriteUserActivity)
            }
            R.id.settings -> {
                val toSettingsActivity =
                    HomeFragmentDirections.actionHomeFragmentToSettingsActivity()
                findNavController().navigate(toSettingsActivity)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setSearchData(items : List<ItemsItem>) {
        binding.rvGithubUser.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvGithubUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvGithubUser.addItemDecoration(itemDecoration)
        val adapter = ListUserAdapter(items)
        binding.rvGithubUser.adapter = adapter
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showDetailUser(data)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE

    }

    private fun showMainInfo (data: MainInfoData) {
        if (data.visible) {
            binding.mainInfo.tvInfo.text = data.infoText
            Glide.with(this)
                .load(data.infoImg)
                .error(R.drawable.image_error)
                .into(binding.mainInfo.imgInfo)
            binding.mainInfo.root.visibility = View.VISIBLE
        } else {
            binding.mainInfo.root.visibility = View.GONE
        }
    }

    private fun showSearchUser(isLoading: Boolean) {
        if (isLoading) binding.rvGithubUser.visibility = View.VISIBLE  else binding.rvGithubUser.visibility = View.GONE

    }

    private fun showDetailUser(userData: ItemsItem){
        val toDetailUserActivity =
            HomeFragmentDirections.actionHomeFragmentToUserDetailActivity(
                userData.login
            )
        findNavController().navigate(toDetailUserActivity)
    }

    private fun obtainViewModel(activity: AppCompatActivity): HomeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(HomeViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}