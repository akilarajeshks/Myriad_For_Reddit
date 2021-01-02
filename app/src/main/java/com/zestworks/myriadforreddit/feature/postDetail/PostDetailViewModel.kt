package com.zestworks.myriadforreddit.feature.postDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zestworks.myriadforreddit.data.RedditNetworkService
import com.zestworks.myriadforreddit.data.postdetail.PostDetailPageSource
import com.zestworks.myriadforreddit.data.postdetail.PostDetailUIData
import kotlinx.coroutines.flow.Flow

class PostDetailViewModel(private val redditNetworkService: RedditNetworkService) : ViewModel(){

    lateinit var flow: Flow<PagingData<PostDetailUIData>>

    fun onUIStart(postPermaLink: String) {
        flow = Pager(
            PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = false)
        ) {
            val bestListPagingSource =
                PostDetailPageSource(redditNetworkService, postPermaLink)
            bestListPagingSource
        }.flow
            .cachedIn(viewModelScope)
    }
}