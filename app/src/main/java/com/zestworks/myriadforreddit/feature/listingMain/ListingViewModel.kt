package com.zestworks.myriadforreddit.feature.listingMain

import androidx.lifecycle.ViewModel
import com.zestworks.myriadforreddit.data.Repository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@ActivityScoped
class ListingViewModel(private val redditRepository: Repository) : ViewModel() {

    fun onStarted() {
        GlobalScope.launch {
            val listOfBestResponse = redditRepository.getListOfBestResponse()
            listOfBestResponse
        }
    }
}