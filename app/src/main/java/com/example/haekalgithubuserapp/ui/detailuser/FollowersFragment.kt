package com.example.haekalgithubuserapp.ui.detailuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haekalgithubuserapp.databinding.FragmentFollowersBinding
import com.example.haekalgithubuserapp.helper.ViewModelFactory
import com.example.haekalgithubuserapp.model.FollowResponseItem

/**
 * Class that organize Followers tab in TabLayout.
 */
class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDetailViewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = arguments?.getString(USER_NAME)

        userDetailViewModel = obtainViewModel(activity as AppCompatActivity)

        userDetailViewModel.followerList.observe(this, { followerList->
            followerList?.let { setFollowerList(it) }
        })

        userDetailViewModel.showFollowerList(userId.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private fun setFollowerList(items: List<FollowResponseItem>) {
        binding.rvFollower.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollower.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvFollower.addItemDecoration(itemDecoration)
        val adapter = FollowersAdapter(items)
        binding.rvFollower.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): UserDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserDetailViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /**
     * Static function that use for receive data via arguments from UserDetailActivity through SectionsPagerAdpater.
     *
     * @property USER_NAME is key of value.
     */
    companion object {
        private const val USER_NAME = "user_name"

        /**
         * Function that save the given argument to bundle.
         *
         * @param username is string data type, got from UserDetailActivity.
         */
        @JvmStatic
        fun userName(username: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_NAME, username)
                }
            }
    }
}