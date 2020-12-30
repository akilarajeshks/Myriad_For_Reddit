package com.zestworks.myriadforreddit.feature.listingMain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.myriadforreddit.R
import com.zestworks.myriadforreddit.data.UIData

class ListingBestAdapter(diffCallBack: DiffUtil.ItemCallback<UIData>) :
    PagingDataAdapter<UIData, ChildrenViewHolder>(diffCallBack) {
    override fun onBindViewHolder(holder: ChildrenViewHolder, position: Int) {
        val item = getItem(position)
        holder.subRedditName.text = item!!.subReddit
        holder.title.text = item.title

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildrenViewHolder {
        return ChildrenViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.best_list_item, parent, false)
        )
    }
}

class ChildrenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val subRedditName: TextView = itemView.findViewById(R.id.subreddit_name)
    val title: TextView = itemView.findViewById(R.id.title_name)
}
