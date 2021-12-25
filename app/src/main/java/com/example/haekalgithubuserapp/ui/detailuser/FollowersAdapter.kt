package com.example.haekalgithubuserapp.ui.detailuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.haekalgithubuserapp.model.FollowResponseItem
import com.example.haekalgithubuserapp.R
import com.example.haekalgithubuserapp.databinding.ItemRowUserBinding

class FollowersAdapter(private val listFollower: List<FollowResponseItem>) : RecyclerView.Adapter<FollowersAdapter.FollowerViewHolder>(){

    class FollowerViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val data = listFollower[position]
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .error(R.drawable.image_error)
            .circleCrop()
            .into(holder.binding.imgUserPhoto)
        holder.binding.tvUsername.text = data.login
        holder.binding.tvHtmlUrl.text = data.htmlUrl
    }

    override fun getItemCount(): Int = listFollower.size
}