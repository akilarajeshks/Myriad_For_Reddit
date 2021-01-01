package com.zestworks.myriadforreddit.feature.listingMain

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zestworks.myriadforreddit.R
import com.zestworks.myriadforreddit.data.listingMain.ListingMainUIData
import com.zestworks.myriadforreddit.feature.listingMain.Click.POST
import com.zestworks.myriadforreddit.feature.listingMain.Click.SUBREDDIT

class ListingBestAdapter(
    diffCallBack: DiffUtil.ItemCallback<ListingMainUIData>,
    val onClickListener: (Click) -> Unit
) :
    PagingDataAdapter<ListingMainUIData, ChildrenViewHolder>(diffCallBack) {
    override fun onBindViewHolder(holder: ChildrenViewHolder, position: Int) {
        val item = getItem(position)
        holder.subRedditName.text = item!!.subReddit
        holder.subRedditName.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        holder.title.text = item.title
        Glide.with(holder.itemView.context).load(item.thumbnail).into(holder.thumbnailImage)

        holder.subRedditName.setOnClickListener {
            SUBREDDIT.link = item.subReddit
            onClickListener(SUBREDDIT)
        }

        holder.itemView.setOnClickListener {
            POST.link = item.permalink
            onClickListener(POST)
        }
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
    val thumbnailImage: ImageView = itemView.findViewById(R.id.thumbnail_image)
}

enum class Click(var link: String) {
    SUBREDDIT(""),
    POST("")
}