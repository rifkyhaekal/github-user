package com.example.haekalgithubuserapp.ui.detailuser

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.haekalgithubuserapp.R
import com.example.haekalgithubuserapp.database.FavoriteUser
import com.example.haekalgithubuserapp.databinding.ActivityUserDetailBinding
import com.example.haekalgithubuserapp.helper.ViewModelFactory
import com.example.haekalgithubuserapp.model.MainInfoData
import com.example.haekalgithubuserapp.model.UserDetailResponse
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Class of User Detail layout.
 */
class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var userDetailViewModel: UserDetailViewModel
    private var favoriteUser: FavoriteUser? = null
    private var userNameDb: String? = null
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra(USER_ID) == null) {
            userId = UserDetailActivityArgs.fromBundle(intent.extras as Bundle).id
        } else {
            userId = intent.getStringExtra(USER_ID)
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = userId
        }

        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }

        userDetailViewModel = obtainViewModel(this@UserDetailActivity)

        userDetailViewModel.userDetail.observe(this, { userDetail ->
            userDetail?.let {
                setUserDetail(it)
                favoriteUser = FavoriteUser( username = it.login, htmlUrl = it.htmlUrl, avatarUrl = it.avatarUrl)
            }
        })

        userDetailViewModel.showDetailUser.observe(this, {
            showDetailUser(it)
        })

        userDetailViewModel.showMainInfo.observe(this, {
            showMainInfo(it)
        })

        userDetailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        userDetailViewModel.getDetailUser(userId!!)

        userDetailViewModel.getUsername(userId!!).observe(this, { userName ->
            userNameDb = userName
            if (userId == userNameDb) {
                binding.btnFavorite.text = getString(R.string.favorited)
                binding.btnFavorite.setBackgroundColor(resources.getColor(R.color.transparent))
                binding.btnFavorite.setStrokeColorResource(R.color.textContentDark)
                binding.btnFavorite.setTextColor(resources.getColor(R.color.textContentDark))
            } else {
                binding.btnFavorite.text = getString(R.string.favorite)
                binding.btnFavorite.setBackgroundColor(resources.getColor(R.color.favorite))
                binding.btnFavorite.setStrokeColorResource(R.color.favorite)
                binding.btnFavorite.setTextColor(resources.getColor(R.color.favoriteText))
            }
        })

        binding.btnFavorite.setOnClickListener {
            if (userId != userNameDb || userNameDb == null) {
                userDetailViewModel.insert(favoriteUser as FavoriteUser)
            } else {
                showAlertDialog(userId!!)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, userId!!)
        binding.viewPager.adapter = sectionsPagerAdapter

        supportActionBar?.elevation = 0f
    }

    /**
     * Function that organize the detail data of user.
     *
     * This function contain progress of binding detail data of user from API request to the layout.
     *
     * @param userDetail UserDetailResoponse data typed, receive input from view model data.
     */
    private fun setUserDetail(userDetail: UserDetailResponse) {
        Glide.with(this@UserDetailActivity)
            .load(userDetail.avatarUrl)
            .circleCrop()
            .into(binding.imgUserPhotoDetail)
        binding.tvUserNameDetail.text = userDetail.name
        binding.tvUserUsernameDetail.text = userDetail.login
        if (userDetail.bio != null) binding.tvBio.text = userDetail.bio else binding.tvBio.visibility = View.GONE
        if (userDetail.company != null) binding.tvCompany.text = userDetail.company else binding.tvCompany.visibility = View.GONE
        if (userDetail.location != null) binding.tvLocation.text = userDetail.location else binding.tvLocation.visibility = View.GONE
        if (userDetail.publicRepos != null) {
            binding.tvRepository.text = userDetail.publicRepos.toString()
        } else {
            binding.tvRepository.visibility = View.GONE
            binding.tvRepositoryText.visibility = View.GONE
        }
        if(userDetail.followers != null && userDetail.following != null) {
            val TAB_FOLLOW = intArrayOf(
                userDetail.followers,
                userDetail.following
            )
            tabLayoutMediator(TAB_FOLLOW)
        } else if (userDetail.followers != null && userDetail.following == null) {
            val TAB_FOLLOW = intArrayOf(
                userDetail.followers,
                0
            )
            tabLayoutMediator(TAB_FOLLOW)
        } else if (userDetail.followers == null && userDetail.following != null) {
            val TAB_FOLLOW = intArrayOf(
                0,
                userDetail.following
            )
            tabLayoutMediator(TAB_FOLLOW)
        } else {
            val TAB_FOLLOW = intArrayOf(
                0,
                0
            )
            tabLayoutMediator(TAB_FOLLOW)
        }
    }

    /**
     * Function that control main information of application
     *
     * This function will display the condition of application like welcoming page, user not found, and no internet connection.
     *
     * @param data is MainInfoData typed, this data class have three parameter for visibility of main info, the text that displayed, and drawebles that displayed.
     */
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

    /**
     * Function for showing user detail
     *
     * This function used to set visibility of user detail.
     *
     * @param isVisible boolean data type, if it is true then it will display detail user and if it is false then it will undisplay detail user.
     */
    private fun showDetailUser(isVisible: Boolean) {
        if (isVisible) {
            binding.csView.visibility = View.VISIBLE
            binding.tabs.visibility = View.VISIBLE
        } else {
            binding.csView.visibility = View.GONE
            binding.tabs.visibility = View.GONE
        }
    }

    /**
     * Function that use for integrate TabLayout and ViewPager2
     *
     * @param arrayFollow is integer array data type, that contain amount of total followers and following of user
     */
    private fun tabLayoutMediator(arrayFollow: IntArray){
        TabLayoutMediator(binding.tabs, binding.viewPager ) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position], arrayFollow[position])
        }.attach()
    }

    /**
     * A function that used for show alert dialog when user choose the favorited button.
     *
     * @param userId username of the profile selected.
     */
    private fun showAlertDialog(userId: String) {
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.AlertDialog)
        with(alertDialogBuilder) {
            setTitle(Html.fromHtml("<b>" + getString(R.string.deleteFavoritedTitle, userId) + "</b>"))
            setMessage(R.string.deleteFavoritedMessage)
            setCancelable(false)
            setPositiveButton(R.string.yes) { _, _ ->
                userDetailViewModel.delete(favoriteUser as FavoriteUser)
            }
            setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    /**
     * A function that used for obtain view model.
     *
     * @param activity an activity context that call this function.
     */
    private fun obtainViewModel(activity: AppCompatActivity): UserDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserDetailViewModel::class.java)
    }

    /**
     * Function that used for showing progress bar
     *
     * @param isLoading boolean data type, if it is true then it will display progress bar and if it is false then it will undisplay progress bar.
     */
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
    }

    /**
     * Static object to define the titles of tablayout.
     *
     * @property TAB_TITLES is an array of integer that contain the titles of tablayout.
     */
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
        const val USER_ID = "user_id"
    }
}