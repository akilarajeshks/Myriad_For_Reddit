package com.zestworks.myriadforreddit.feature.subredditListing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zestworks.myriadforreddit.data.RedditNetworkService
import com.zestworks.myriadforreddit.data.subredditListing.SubredditListingPagingSource
import com.zestworks.myriadforreddit.data.subredditListing.SubredditListingUIData
import kotlinx.coroutines.flow.Flow

class SubredditListingViewModel(private val redditNetworkService: RedditNetworkService) :
    ViewModel() {
    lateinit var flow: Flow<PagingData<SubredditListingUIData>>

    fun onUIStart(subredditLink: String) {
        flow = Pager(
            PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = false)
        ) {
            val bestListPagingSource =
                SubredditListingPagingSource(redditNetworkService, subredditLink)
            bestListPagingSource
        }.flow
            .cachedIn(viewModelScope)
    }
}