package com.zestworks.myriadforreddit.feature.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zestworks.myriadforreddit.data.RedditNetworkService
import kotlinx.coroutines.flow.Flow

class PostViewModel(private val redditNetworkService: RedditNetworkService) : ViewModel(){

    lateinit var flow: Flow<PagingData<PostDetailUIDataItem>>

    fun onUIStart(postPermaLink: String) {
        flow = Pager(
            PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = false)
        ) {
            val bestListPagingSource =
                PostPagingSource(redditNetworkService, postPermaLink)
            bestListPagingSource
        }.flow
            .cachedIn(viewModelScope)
    }
}