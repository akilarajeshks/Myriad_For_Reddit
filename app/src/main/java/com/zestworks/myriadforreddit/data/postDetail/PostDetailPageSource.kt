package com.zestworks.myriadforreddit.data.postDetail

import androidx.paging.PagingSource
import com.zestworks.myriadforreddit.data.RedditNetworkService


class PostDetailPageSource(
    private val networkService: RedditNetworkService,
    private val postPermaLink: String
) :
    PagingSource<String, PostDetailUIData>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostDetailUIData> {
        return try {
            val response = networkService.getPostDetail(
                postPermaLink,
                10,
                after = if (params is LoadParams.Append) params.key else null
            )

            val childrenResponse = response.body()!!
            var page: LoadResult.Page<String, PostDetailUIData> =
                LoadResult.Page(data = mutableListOf(), prevKey = null, nextKey = null)

            childrenResponse.forEachIndexed { index, postResponseItem ->
                if (index == 0) {
                    val data = postResponseItem.data.children.map { children ->
                        PostDetailUIData(
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
                            PostDetailUIData(
                                subreddit = children.data.subredditNamePrefixed,
                                authorName = children.data.author,
                                message = children.data.body,
                                messageType = MessageType.COMMENT
                            )
                        }.toMutableList()
                    data.addAll(page.data)
                    page = page.copy(data = data)
                }
            }
            return page
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
