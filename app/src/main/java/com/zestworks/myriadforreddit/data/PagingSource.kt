package com.zestworks.myriadforreddit.data

import androidx.paging.PagingSource

class BestListPagingSource(
    private val service: RedditNetworkService,
) : PagingSource<String, UIData>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, UIData> {
        return try {
            val response = service.getBestListing(
                10,
                after = if (params is LoadParams.Append) params.key else null
            )
            val children = response.body()!!.data
            LoadResult.Page(
                data = children.children.map {
                    UIData(
                        subReddit = "r/${it.data.subreddit}",
                        subRedditId = it.data.subredditId,
                        articleID = it.data.id,
                        selfText = it.data.selftext,
                        thumbnail = it.data.thumbnail,
                        urlOverriddenByDest = it.data.urlOverriddenByDest,
                        title = it.data.title
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