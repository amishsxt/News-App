package com.example.news_application.Adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news_application.Model.DataModel.News
import com.example.news_application.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class NewsAdapter(private val listener: NewsAdapterListener) :
    PagingDataAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.news_item_layout, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = getItem(position)
        if (newsItem != null) {
            holder.bind(newsItem, listener)
        }
    }

    interface NewsAdapterListener {
        fun onNewsItemClick(newsItem: News)
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.title)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val newsImg: ImageView = itemView.findViewById(R.id.newsImg)
        private val imgProgressBar: ProgressBar = itemView.findViewById(R.id.imgProgressBar)

        fun bind(news: News, listener: NewsAdapterListener) {
            // Binding data to UI components
            Log.d("News Article:" ,news.title.toString())

            title.text = news.title
            description.text = news.description

            if(news.urlToImage!=null){
                setPicture(newsImg, Uri.parse(news.urlToImage))
            }

            // item clicked
            itemView.setOnClickListener {
                listener.onNewsItemClick(news)
            }

        }

        private fun setPicture(imageView: ImageView, imageUri: Uri) {
            showImgProgressBar()

            Picasso.get()
                .load(imageUri)
                .error(R.drawable.news_default)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        hideImgProgressBar()
                    }

                    override fun onError(e: Exception?) {
                        hideImgProgressBar()
                    }
                })
        }

        private fun showImgProgressBar() {
            newsImg.visibility = View.INVISIBLE
            imgProgressBar.visibility = View.VISIBLE
        }

        private fun hideImgProgressBar() {
            newsImg.visibility = View.VISIBLE
            imgProgressBar.visibility = View.GONE
        }
    }

    private class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id.equals(newItem.id)
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.equals(newItem)
        }
    }
}




