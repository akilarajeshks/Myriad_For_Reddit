package com.zestworks.myriadforreddit.feature.home

import androidx.recyclerview.widget.DiffUtil

object HomeDiff : DiffUtil.ItemCallback<HomeUIDataItem>() {
    override fun areItemsTheSame(oldItem: HomeUIDataItem, newItem: HomeUIDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: HomeUIDataItem,
        newItem: HomeUIDataItem
    ): Boolean {
        return oldItem == newItem
    }
}