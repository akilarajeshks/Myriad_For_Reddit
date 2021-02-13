package com.zestworks.myriadforreddit.feature.home

data class HomeUIDataItem(
    val subReddit: String,
    val title: String,
    val id: String,
    val cardType: CardType,
    val permalink: String,
)