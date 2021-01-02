package com.zestworks.myriadforreddit.data.postdetail

data class PostDetailUIData(
    val subreddit: String,
    val authorName: String,
    val message: String,
    val messageType: MessageType
)

enum class MessageType {
    TITLE, COMMENT
}