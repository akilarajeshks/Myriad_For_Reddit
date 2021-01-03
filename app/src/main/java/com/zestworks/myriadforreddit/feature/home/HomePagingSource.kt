package com.zestworks.myriadforreddit.feature.home

import androidx.paging.PagingSource
import com.zestworks.myriadforreddit.data.RedditNetworkService

class HomePagingSource(
    private val service: RedditNetworkService,
) : PagingSource<String, HomeUIDataItem>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, HomeUIDataItem> {
        return try {
            val response = service.getBestListing(
                10,
                after = if (params is LoadParams.Append) params.key else null
            )
            val children = response.body()!!.data
            LoadResult.Page(
                data = children.children.map {
                    HomeUIDataItem(
                        subReddit = it.data.subredditNamePrefixed,
                        selfText = it.data.selftext,
                        thumbnailUrl = it.data.thumbnail,
                        urlOverriddenByDest = it.data.urlOverriddenByDest,
                        title = it.data.title,
                        permalink = it.data.permalink.drop(1),
                        id = it.data.id
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