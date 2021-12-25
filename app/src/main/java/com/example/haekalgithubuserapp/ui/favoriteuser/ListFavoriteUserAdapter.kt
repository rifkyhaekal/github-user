package com.example.haekalgithubuserapp.ui.favoriteuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.haekalgithubuserapp.R
import com.example.haekalgithubuserapp.database.FavoriteUser
import com.example.haekalgithubuserapp.databinding.ItemFavoriteUserBinding
import com.example.haekalgithubuserapp.helper.FavoriteUserDiffCallback
import com.example.haekalgithubuserapp.ui.detailuser.UserDetailActivity

class ListFavoriteUserAdapter : RecyclerView.Adapter<ListFavoriteUserAdapter.ListFavoriteUserViewHolder>() {

    private val favoriteUserList = ArrayList<FavoriteUser>()
    fun setListFavoriteUse(favoriteUserList: List<FavoriteUser>) {
        val diffCallback = FavoriteUserDiffCallback(this.favoriteUserList, favoriteUserList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.favoriteUserList.clear()
        this.favoriteUserList.addAll(favoriteUserList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFavoriteUserViewHolder {
        val binding =ItemFavoriteUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListFavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListFavoriteUserViewHolder, position: Int) {
        holder.bind(favoriteUserList[position])
    }

    override fun getItemCount(): Int = favoriteUserList.size

    inner class ListFavoriteUserViewHolder(private val binding: ItemFavoriteUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (favoriteUser: FavoriteUser) {
            with(binding) {
                tvUsername.text = favoriteUser.username
                tvHtmlUrl.text = favoriteUser.htmlUrl
                Glide.with(itemView.context)
                    .load(favoriteUser.avatarUrl)
                    .error(R.drawable.image_error)
                    .circleCrop()
                    .into(imgUserPhoto)
                cvFavoriteUser.setOnClickListener{
                    val intent = Intent(it.context, UserDetailActivity::class.java)
                    intent.putExtra(UserDetailActivity.USER_ID, favoriteUser.username)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}