package com.zestworks.myriadforreddit.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.myriadforreddit.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var homeViewModel: HomeViewModel

    private val pagingDataAdapter = HomePagingDataAdapter(HomeDiff) {
        //navigate here
        when (it) {
            Click.SUBREDDIT -> {
                val actionListingMainFragmentToSubredditListingFragment =
                    HomeFragmentDirections.actionListingMainFragmentToSubredditListingFragment(
                        it.link
                    )
                findNavController().navigate(actionListingMainFragmentToSubredditListingFragment)
            }
            Click.POST -> {
                val actionListingMainFragmentToPostDetailFragment =
                    HomeFragmentDirections.actionListingMainFragmentToPostDetailFragment(it.link)
                findNavController().navigate(actionListingMainFragmentToPostDetailFragment)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listing_main, container, false)
    }

    override fun onStart() {
        super.onStart()

        requireView().findViewById<RecyclerView>(R.id.recycler_list).apply {
            adapter = pagingDataAdapter
            layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
        }
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.flow.collect {
                pagingDataAdapter.submitData(it)
            }
        }
    }
}


