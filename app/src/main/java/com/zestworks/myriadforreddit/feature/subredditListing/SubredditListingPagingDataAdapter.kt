package com.zestworks.myriadforreddit.feature.subredditListing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.myriadforreddit.R
import com.zestworks.myriadforreddit.data.subredditlisting.SubredditListingUIData

class SubredditListingPagingDataAdapter(itemCallback: DiffUtil.ItemCallback<SubredditListingUIData>) :
    PagingDataAdapter<SubredditListingUIData, SubredditVewHolder>(itemCallback) {
    override fun onBindViewHolder(holder: SubredditVewHolder, position: Int) {
        val item = getItem(position)
        holder.title.text = item!!.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubredditVewHolder {
        return SubredditVewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.best_list_item, parent, false)
        )
    }
}

class SubredditVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.title_name)
}