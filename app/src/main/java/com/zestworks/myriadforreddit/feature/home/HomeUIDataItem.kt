package com.zestworks.myriadforreddit.feature.home

data class HomeUIDataItem(
    val subReddit: String,
    val title: String,
    val id :String,
    val thumbnailUrl: String,
    val urlOverriddenByDest: String?,
    //post content below title.
    val selfText: String,
    val permalink: String
)