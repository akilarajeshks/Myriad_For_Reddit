package com.zestworks.myriadforreddit.data

import retrofit2.Response
import retrofit2.http.GET

interface RedditNetworkService {

    @GET("best")
    suspend fun getBestListing(): Response<BestListingResponse>
}