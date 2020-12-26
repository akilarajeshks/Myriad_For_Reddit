package com.zestworks.myriadforreddit.data

import retrofit2.Response

class RedditRepository(private val redditNetworkService: RedditNetworkService) : Repository{
    override suspend fun getListOfBestResponse(): Response<BestListingResponse> {
        return redditNetworkService.getBestListing()
    }
}