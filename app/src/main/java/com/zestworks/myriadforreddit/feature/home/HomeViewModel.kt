package com.zestworks.myriadforreddit.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zestworks.myriadforreddit.data.RedditNetworkService
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow

@ActivityScoped
class HomeViewModel(private val redditNetworkService: RedditNetworkService) : ViewModel() {
    var flow: Flow<PagingData<HomeUIDataItem>> = Pager(
        PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = false)
    ) {
        val bestListPagingSource = HomePagingSource(redditNetworkService)
        bestListPagingSource
    }.flow
        .cachedIn(viewModelScope)
}