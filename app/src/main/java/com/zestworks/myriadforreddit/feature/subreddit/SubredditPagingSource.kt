package com.zestworks.myriadforreddit.feature.subreddit

import androidx.paging.PagingSource
import com.zestworks.myriadforreddit.data.RedditNetworkService

class SubredditPagingSource(
    private val networkService: RedditNetworkService,
    private val subredditLink: String
) :
    PagingSource<String, SubredditUIDataItem>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, SubredditUIDataItem> {
        return try {
            val response = networkService.getSubredditListing(
                subredditLink,
                10,
                after = if (params is LoadParams.Append) params.key else null
            )
            val children = response.body()!!.data
            LoadResult.Page(
                data = children.children.map {
                    SubredditUIDataItem(
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