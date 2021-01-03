package com.zestworks.myriadforreddit.data

import com.zestworks.myriadforreddit.feature.post.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditNetworkService {

    @GET("/best")
    suspend fun getBestListing(
        @Query("count") count: Int,
        @Query("after") after: String? = null
    ): Response<BestListingResponse>

    @GET("/{link}")
    suspend fun getSubredditListing(
        @Path("link") link: String,
        @Query("count") count: Int,
        @Query("after") after: String? = null
    ): Response<BestListingResponse>

    @GET("/{link}")
    suspend fun getPostDetail(
        @Path("link") link: String,
        @Query("count") count: Int,
        @Query("after") after: String? = null
    ): Response<PostResponse>
}