package com.zestworks.myriadforreddit.feature.postDetail

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
import com.zestworks.myriadforreddit.data.postdetail.PostDetailUIData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PostDetailFragment : Fragment() {

    @Inject
    lateinit var postDetailViewModel: PostDetailViewModel

    private val pagingDataAdapter = PostDetailPagingDataAdapter(ListDiff)
    private val args: PostDetailFragmentArgs by navArgs()

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
            layoutManager = LinearLayoutManager(this@PostDetailFragment.requireContext())
        }

        lifecycleScope.launch {
            postDetailViewModel.onUIStart(args.postPermalink)
            postDetailViewModel.flow.collect {
                pagingDataAdapter.submitData(it)
            }
        }

    }
}

object ListDiff : DiffUtil.ItemCallback<PostDetailUIData>() {
    override fun areItemsTheSame(
        oldItem: PostDetailUIData,
        newItem: PostDetailUIData
    ): Boolean {
        return oldItem.message == newItem.message
    }

    override fun areContentsTheSame(
        oldItem: PostDetailUIData,
        newItem: PostDetailUIData
    ): Boolean {
        return oldItem == newItem
    }
}
