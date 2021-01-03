package com.zestworks.myriadforreddit.feature.subreddit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zestworks.myriadforreddit.data.RedditNetworkService
import kotlinx.coroutines.flow.Flow

class SubredditViewModel(private val redditNetworkService: RedditNetworkService) :
    ViewModel() {
    lateinit var flow: Flow<PagingData<SubredditUIDataItem>>

    fun onUIStart(subredditLink: String) {
        flow = Pager(
            PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = false)
        ) {
            val bestListPagingSource =
                SubredditPagingSource(redditNetworkService, subredditLink)
            bestListPagingSource
        }.flow
            .cachedIn(viewModelScope)
    }
}