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
                    when {
                        it.data.selftext.isNotEmpty() -> {
                            HomeUIDataItem(
                                subReddit = it.data.subredditNamePrefixed,
                                cardType = CardType.TextCard(selfText = it.data.selftext),
                                title = it.data.title,
                                permalink = it.data.permalink.drop(1),
                                id = it.data.id
                            )
                        }
                        it.data.thumbnail != "self" -> {
                            HomeUIDataItem(
                                subReddit = it.data.subredditNamePrefixed,
                                cardType = CardType.ImageCard(thumbnailUrl = it.data.thumbnail),
                                title = it.data.title,
                                permalink = it.data.permalink.drop(1),
                                id = it.data.id
                            )
                        }
                        it.data.urlOverriddenByDest.isNullOrEmpty().not() -> {
                            HomeUIDataItem(
                                subReddit = it.data.subredditNamePrefixed,
                                cardType = CardType.UrlCard(urlOverriddenByDest = it.data.urlOverriddenByDest),
                                title = it.data.title,
                                permalink = it.data.permalink.drop(1),
                                id = it.data.id
                            )
                        }
                        else ->{
                            HomeUIDataItem(
                                subReddit = it.data.subredditNamePrefixed,
                                cardType = CardType.TitleCard,
                                title = it.data.title,
                                permalink = it.data.permalink.drop(1),
                                id = it.data.id
                            )
                        }
                    }
                },
                prevKey = null,
                nextKey = children.after
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}