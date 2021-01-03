package com.zestworks.myriadforreddit.feature.subreddit

import androidx.recyclerview.widget.DiffUtil

object SubredditDiff : DiffUtil.ItemCallback<SubredditUIDataItem>() {
    override fun areItemsTheSame(
        oldItem: SubredditUIDataItem,
        newItem: SubredditUIDataItem
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: SubredditUIDataItem,
        newItem: SubredditUIDataItem
    ): Boolean {
        return oldItem == newItem
    }
}