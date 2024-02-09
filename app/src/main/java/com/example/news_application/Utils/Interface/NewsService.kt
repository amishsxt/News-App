package com.example.news_application.Utils.Interface

import com.example.news_application.Model.DataModel.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("everything")
    suspend fun getNews(
        @Query("domains") domains: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int
    ): Response<NewsResponse>
}

