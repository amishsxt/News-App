package com.example.news_application.View

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.news_application.Model.DataModel.News
import com.example.news_application.R
import com.example.news_application.databinding.ActivityNewsBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class NewsActivity : AppCompatActivity() {

    private lateinit var xml : ActivityNewsBinding
    private lateinit var news : News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //root
        xml = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(xml.root)

        val receivedIntent = intent
        news = receivedIntent.getSerializableExtra("news") as News

        setUpData()

        xml.backBtn.setOnClickListener {
            finish()
        }

        xml.readMoreBtn.setOnClickListener {
            openWebsite()
        }

    }

    private fun setUpData() {
        Log.d("checkContent", news.content.toString())

        xml.title.text = news.title
        xml.description.text = news.description
        xml.author.text = "- by " + news.author
        xml.content.text = news.content

        if(news.urlToImage!=null){
            setPicture(xml.newsImg, Uri.parse(news.urlToImage))
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
        xml.newsImg.visibility = View.INVISIBLE
        xml.imgProgressBar.visibility = View.VISIBLE
    }

    private fun hideImgProgressBar() {
        xml.newsImg.visibility = View.VISIBLE
        xml.imgProgressBar.visibility = View.GONE
    }

    private fun openWebsite() {
        if(news.url!=null){
            Log.d("sourceUrl",news.url.toString())

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
            startActivity(intent)
        }else{
            Log.d("sourceUrl","is null")
        }
    }
}