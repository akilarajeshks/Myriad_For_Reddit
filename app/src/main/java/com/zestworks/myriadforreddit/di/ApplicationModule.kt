package com.zestworks.myriadforreddit.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.zestworks.myriadforreddit.data.RedditNetworkService
import com.zestworks.myriadforreddit.data.RedditRepository
import com.zestworks.myriadforreddit.feature.listingMain.ListingViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object ApplicationModule {

    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val okHttClient = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context))
            .build()
        return Retrofit.Builder()
            .client(okHttClient).baseUrl("https://api.reddit.com")
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
    fun provideListingViewModel(redditNetworkService: RedditNetworkService): ListingViewModel {
        return ListingViewModel(redditNetworkService)
    }
}