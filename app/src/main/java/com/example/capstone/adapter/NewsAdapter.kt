package com.example.capstone.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.capstone.R
import com.example.capstone.data.local.entity.NewsEntity
import com.example.capstone.databinding.ItemListNewsBinding
import com.example.capstone.utils.DateFormatter

class NewsAdapter : ListAdapter<NewsEntity, NewsAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class MyViewHolder(private val binding: ItemListNewsBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(news: NewsEntity) {
            binding.tvItemTitle.text = news.title
            binding.tvItemPublishedDate.text = DateFormatter.formatDate(news.publishedAt)
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.imgPoster)
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(news.url)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}