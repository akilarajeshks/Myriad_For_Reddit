package com.zestworks.myriadforreddit.data

data class UIData(
    val subReddit : String,
    val subRedditId : String,
    val articleID : String,
    val title : String,
    val thumbnail: String,
    val urlOverriddenByDest: String,
    //post content below title.
    val selfText:String
)