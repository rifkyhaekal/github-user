package com.example.haekalgithubuserapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.haekalgithubuserapp.model.ItemsItem
import com.example.haekalgithubuserapp.R
import com.example.haekalgithubuserapp.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    // Implementasi viewbinding
    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listUser[position]
        holder.binding.tvUsername.text = data.login
        holder.binding.tvHtmlUrl.text = data.htmlUrl
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .error(R.drawable.image_error)
            .circleCrop()
            .into(holder.binding.imgUserPhoto)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback{
        fun onItemClicked (data: ItemsItem)
    }

}