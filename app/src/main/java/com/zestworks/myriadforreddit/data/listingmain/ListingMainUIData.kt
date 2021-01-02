package com.zestworks.myriadforreddit.data.listingmain

data class ListingMainUIData(
    val subReddit: String,
    val title: String,
    val articleID :String,
    val thumbnail: String,
    val urlOverriddenByDest: String?,
    //post content below title.
    val selfText: String,
    val permalink: String
)