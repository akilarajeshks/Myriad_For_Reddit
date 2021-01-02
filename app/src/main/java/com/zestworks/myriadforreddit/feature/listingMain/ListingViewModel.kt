package com.zestworks.myriadforreddit.feature.listingMain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zestworks.myriadforreddit.data.listingmain.ListingMainPageSource
import com.zestworks.myriadforreddit.data.RedditNetworkService
import com.zestworks.myriadforreddit.data.listingmain.ListingMainUIData
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow

@ActivityScoped
class ListingViewModel(private val redditNetworkService: RedditNetworkService) : ViewModel() {
    var flow: Flow<PagingData<ListingMainUIData>> = Pager(
        PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = false)
    ) {
        val bestListPagingSource = ListingMainPageSource(redditNetworkService)
        bestListPagingSource
    }.flow
        .cachedIn(viewModelScope)
}