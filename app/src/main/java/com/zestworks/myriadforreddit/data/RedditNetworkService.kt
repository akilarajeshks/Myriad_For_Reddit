package com.zestworks.myriadforreddit.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditNetworkService {

    @GET("/best")
    suspend fun getBestListing(
        @Query("count") count: Int,
        @Query("after") after: String? = null
    ): Response<BestListingResponse>
}