package com.zestworks.myriadforreddit.feature.post

data class PostDetailUIDataItem(
    val subreddit: String,
    val authorName: String,
    //TODO make it specific - add sealed class.
    val message: String,
    val messageType: MessageType
)

enum class MessageType {
    TITLE, COMMENT
}