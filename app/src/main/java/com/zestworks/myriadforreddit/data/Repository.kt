package com.zestworks.myriadforreddit.data

import retrofit2.Response

interface Repository {
    suspend fun getListOfBestResponse(): Response<BestListingResponse>
}