package com.zestworks.myriadforreddit.feature.listingMain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zestworks.myriadforreddit.data.BestListPagingSource
import com.zestworks.myriadforreddit.data.RedditNetworkService
import com.zestworks.myriadforreddit.data.UIData
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow

@ActivityScoped
class ListingViewModel(private val redditNetworkService: RedditNetworkService) : ViewModel() {
    var flow: Flow<PagingData<UIData>> = Pager(
        PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = false)
    ) {
        val bestListPagingSource = BestListPagingSource(redditNetworkService)
        bestListPagingSource
    }.flow
        .cachedIn(viewModelScope)
}