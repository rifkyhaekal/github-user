package com.example.haekalgithubuserapp.ui.detailuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.haekalgithubuserapp.model.FollowResponseItem
import com.example.haekalgithubuserapp.R
import com.example.haekalgithubuserapp.databinding.ItemRowUserBinding

class FollowingAdapter(private val listFollowing: List<FollowResponseItem>) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    class FollowingViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val data = listFollowing[position]
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .error(R.drawable.image_error)
            .circleCrop()
            .into(holder.binding.imgUserPhoto)
        holder.binding.tvUsername.text = data.login
        holder.binding.tvHtmlUrl.text = data.htmlUrl
    }

    override fun getItemCount(): Int = listFollowing.size
}