package com.example.news_application.Model.Repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.news_application.Model.DataModel.News
import com.example.news_application.Utils.Interface.NewsApiService
import javax.inject.Inject

class NewsRepo @Inject constructor(private val apiService: NewsApiService) {

    fun getNewsPagingSource(domains: String, apiKey: String): PagingSource<Int, News> {
        return object : PagingSource<Int, News>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
                return try {
                    val page = params.key ?: 1
                    val response = apiService.getNews(domains, apiKey, page)
                    if (response.isSuccessful) {
                        val newsList = response.body()?.articles ?: emptyList()
                        Log.d("NewsRepo", "Number of items loaded: ${newsList.size}")

                        LoadResult.Page(
                            data = newsList,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (newsList.isEmpty()) null else page + 1
                        )
                    } else {
                        LoadResult.Error(Exception("Failed to fetch data"))
                    }
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, News>): Int? {
                return state.anchorPosition
            }
        }
    }
}

