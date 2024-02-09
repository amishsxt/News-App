package com.example.news_application.ViewModel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news_application.Model.DataModel.News
import com.example.news_application.Model.Repository.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepo: NewsRepo) : ViewModel() {

    val newsFlow: Flow<PagingData<News>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { newsRepo.getNewsPagingSource("wsj.com", "01c59c66f2e845b9aa827924950d4f90") }
    ).flow

}


