package com.zestworks.myriadforreddit.feature.subreddit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.myriadforreddit.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SubredditFragment : Fragment() {

    @Inject
    lateinit var subredditViewModel: SubredditViewModel

    private val pagingDataAdapter = SubredditListingPagingDataAdapter(SubredditDiff)
    private val args: SubredditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listing_main, container, false)
    }

    override fun onStart() {
        super.onStart()
        requireView().findViewById<RecyclerView>(R.id.recycler_list).apply {
            adapter = pagingDataAdapter
            layoutManager = LinearLayoutManager(this@SubredditFragment.requireContext())
        }

        lifecycleScope.launch {
            subredditViewModel.onUIStart(args.subredditNamePrefixed)
            subredditViewModel.flow.collect {
                pagingDataAdapter.submitData(it)
            }
        }
    }
}


