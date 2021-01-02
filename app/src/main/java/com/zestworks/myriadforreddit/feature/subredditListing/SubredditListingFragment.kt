package com.zestworks.myriadforreddit.feature.subredditListing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.myriadforreddit.R
import com.zestworks.myriadforreddit.data.subredditlisting.SubredditListingUIData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SubredditListingFragment : Fragment() {

    @Inject
    lateinit var subredditListingViewModel: SubredditListingViewModel

    private val pagingDataAdapter = SubredditListingPagingDataAdapter(ListDiff)
    private val args: SubredditListingFragmentArgs by navArgs()

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
            layoutManager = LinearLayoutManager(this@SubredditListingFragment.requireContext())
        }

        lifecycleScope.launch {
            subredditListingViewModel.onUIStart(args.subredditNamePrefixed)
            subredditListingViewModel.flow.collect {
                pagingDataAdapter.submitData(it)
            }
        }
    }
}

object ListDiff : DiffUtil.ItemCallback<SubredditListingUIData>() {
    override fun areItemsTheSame(
        oldItem: SubredditListingUIData,
        newItem: SubredditListingUIData
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: SubredditListingUIData,
        newItem: SubredditListingUIData
    ): Boolean {
        return oldItem == newItem
    }
}
