package com.zestworks.myriadforreddit.feature.listingMain

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.myriadforreddit.R
import com.zestworks.myriadforreddit.data.UIData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListingMainFragment : Fragment() {

    @Inject
    lateinit var listingViewModel: ListingViewModel

    private val pagingDataAdapter = ListingBestAdapter(ListDiff)

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
            layoutManager = LinearLayoutManager(this@ListingMainFragment.requireContext())
        }
        GlobalScope.launch {
            listingViewModel.flow.collectLatest {
                pagingDataAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            pagingDataAdapter.loadStateFlow.collectLatest {
                Log.d("Data adapter state :", "$it")
            }
        }
    }
}

object ListDiff : DiffUtil.ItemCallback<UIData>() {
    override fun areItemsTheSame(oldItem: UIData, newItem: UIData): Boolean {
        return oldItem.articleID == newItem.articleID
    }

    override fun areContentsTheSame(oldItem: UIData, newItem: UIData): Boolean {
        return oldItem == newItem
    }
}