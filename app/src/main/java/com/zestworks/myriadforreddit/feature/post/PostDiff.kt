package com.zestworks.myriadforreddit.feature.post

import androidx.recyclerview.widget.DiffUtil

object PostDiff : DiffUtil.ItemCallback<PostDetailUIDataItem>() {
    override fun areItemsTheSame(
        oldItem: PostDetailUIDataItem,
        newItem: PostDetailUIDataItem
    ): Boolean {
        return oldItem.message == newItem.message
    }

    override fun areContentsTheSame(
        oldItem: PostDetailUIDataItem,
        newItem: PostDetailUIDataItem
    ): Boolean {
        return oldItem == newItem
    }
}