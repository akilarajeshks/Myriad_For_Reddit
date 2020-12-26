package com.zestworks.myriadforreddit.di

import com.zestworks.myriadforreddit.data.RedditNetworkService
import com.zestworks.myriadforreddit.data.RedditRepository
import com.zestworks.myriadforreddit.feature.listingMain.ListingViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object ApplicationModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.reddit.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }


    @Provides
    fun provideNetworkService(retrofit: Retrofit): RedditNetworkService {
        return retrofit.create(RedditNetworkService::class.java)
    }


    @Provides
    fun provideRepository(redditNetworkService: RedditNetworkService): RedditRepository {
        return RedditRepository(redditNetworkService)
    }

    @Provides
    fun provideListingViewModel(redditRepository: RedditRepository) : ListingViewModel{
        return ListingViewModel(redditRepository)
    }
}