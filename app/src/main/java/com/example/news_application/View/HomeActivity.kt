package com.example.news_application.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_application.Adapter.NewsAdapter
import com.example.news_application.Model.DataModel.News
import com.example.news_application.R
import com.example.news_application.ViewModel.NewsViewModel
import com.example.news_application.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NewsAdapter.NewsAdapterListener {

    private lateinit var xml : ActivityHomeBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //root
        xml = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(xml.root)

        showProgressBar()

        // Initialize ViewModel and Adapter
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        adapter = NewsAdapter(this)

        setAdapter()

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        // Observe the paging data from the ViewModel
        lifecycleScope.launch {
            viewModel.newsFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

    }

    private fun setAdapter() {
        // Set up RecyclerView with adapter
        xml.recyclerView.adapter = adapter
        xml.recyclerView.layoutManager = LinearLayoutManager(this)

        hideProgressBar()
    }

    override fun onNewsItemClick(newsItem: News) {
        val intent = Intent(this, NewsActivity::class.java)
        intent.putExtra("news", newsItem)
        startActivity(intent)
    }

    private fun showProgressBar() {
        xml.recyclerView.visibility = View.INVISIBLE
        xml.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        xml.recyclerView.visibility = View.VISIBLE
        xml.progressBar.visibility = View.GONE
    }

}