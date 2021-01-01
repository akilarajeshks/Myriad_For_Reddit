package com.zestworks.myriadforreddit.data.subredditListing

import androidx.paging.PagingSource
import com.zestworks.myriadforreddit.data.RedditNetworkService

class SubredditListingPagingSource(
    private val networkService: RedditNetworkService,
    private val subredditLink: String
) :
    PagingSource<String, SubredditListingUIData>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, SubredditListingUIData> {
        return try {
            val response = networkService.getSubredditListing(
                subredditLink,
                10,
                after = if (params is LoadParams.Append) params.key else null
            )
            val children = response.body()!!.data
            LoadResult.Page(
                data = children.children.map {
                    SubredditListingUIData(
                        title = it.data.title,
                    )
                },
                prevKey = null,
                nextKey = children.after
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}