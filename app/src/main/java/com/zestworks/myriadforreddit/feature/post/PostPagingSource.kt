package com.zestworks.myriadforreddit.feature.post

import androidx.paging.PagingSource
import com.zestworks.myriadforreddit.data.RedditNetworkService


class PostPagingSource(
    private val networkService: RedditNetworkService,
    private val postPermaLink: String
) :
    PagingSource<String, PostDetailUIDataItem>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostDetailUIDataItem> {
        return try {
            val response = networkService.getPostDetail(
                postPermaLink,
                10,
                after = if (params is LoadParams.Append) params.key else null
            )

            val childrenResponse = response.body()!!
            var page: LoadResult.Page<String, PostDetailUIDataItem> =
                LoadResult.Page(data = mutableListOf(), prevKey = null, nextKey = null)

            childrenResponse.forEachIndexed { index, postResponseItem ->
                if (index == 0) {
                    val data = postResponseItem.data.children.map { children ->
                        PostDetailUIDataItem(
                            subreddit = children.data.subredditNamePrefixed,
                            authorName = children.data.author,
                            message = children.data.title,
                            messageType = MessageType.TITLE
                        )
                    }.toMutableList()
                    data.addAll(page.data)

                    page = page.copy(
                        data = data,
                    )
                } else {
                    val data = postResponseItem.data.children.filter { it.data.subredditId != null }
                        .map { children ->
                            PostDetailUIDataItem(
                                subreddit = children.data.subredditNamePrefixed,
                                authorName = children.data.author,
                                message = children.data.body,
                                messageType = MessageType.COMMENT
                            )
                        }.toMutableList()
                    data.addAll(0,page.data)
                    page = page.copy(data = data)
                }
            }
            return page
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
