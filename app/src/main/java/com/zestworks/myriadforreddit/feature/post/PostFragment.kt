package com.zestworks.myriadforreddit.feature.post

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
class PostFragment : Fragment() {

    @Inject
    lateinit var postViewModel: PostViewModel

    private val pagingDataAdapter = PostPagingDataAdapter(PostDiff)
    private val args: PostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subreddit, container, false)
    }

    override fun onStart() {
        super.onStart()

        requireView().findViewById<RecyclerView>(R.id.recycler_list).apply {
            adapter = pagingDataAdapter
            layoutManager = LinearLayoutManager(this@PostFragment.requireContext())
        }

        lifecycleScope.launch {
            postViewModel.onUIStart(args.postPermalink)
            postViewModel.flow.collect {
                pagingDataAdapter.submitData(it)
            }
        }

    }
}


