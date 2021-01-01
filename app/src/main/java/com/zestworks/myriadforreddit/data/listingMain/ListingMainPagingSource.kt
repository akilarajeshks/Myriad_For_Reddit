package com.zestworks.myriadforreddit.data.listingMain

import androidx.paging.PagingSource
import com.zestworks.myriadforreddit.data.RedditNetworkService

class ListingMainPageSource(
    private val service: RedditNetworkService,
) : PagingSource<String, ListingMainUIData>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, ListingMainUIData> {
        return try {
            val response = service.getBestListing(
                10,
                after = if (params is LoadParams.Append) params.key else null
            )
            val children = response.body()!!.data
            LoadResult.Page(
                data = children.children.map {
                    ListingMainUIData(
                        subReddit = it.data.subredditNamePrefixed,
                        selfText = it.data.selftext,
                        thumbnail = it.data.thumbnail,
                        urlOverriddenByDest = it.data.urlOverriddenByDest,
                        title = it.data.title,
                        permalink = it.data.permalink,
                        articleID = it.data.id
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