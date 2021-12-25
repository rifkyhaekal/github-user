package com.example.haekalgithubuserapp.ui.detailuser

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: UserDetailActivity, userId: String): FragmentStateAdapter(activity) {
    private val userId = userId
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment.userName(userId)
            1 -> fragment = FollowingFragment.userName(userId)
        }
        return fragment as Fragment
    }
}