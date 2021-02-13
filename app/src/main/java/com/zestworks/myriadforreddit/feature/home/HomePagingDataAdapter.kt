package com.zestworks.myriadforreddit.feature.home

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
import com.zestworks.myriadforreddit.feature.home.Click.POST
import com.zestworks.myriadforreddit.feature.home.Click.SUBREDDIT

class HomePagingDataAdapter(
    diffCallBack: DiffUtil.ItemCallback<HomeUIDataItem>,
    val onClickListener: (Click) -> Unit
) : PagingDataAdapter<HomeUIDataItem, ChildViewHolder>(diffCallBack) {

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { listingDataItem: HomeUIDataItem ->

            when (item.cardType) {
                is CardType.TextCard -> {
                    val textViewHolder = holder as TextViewHolder
                    textViewHolder.subRedditName.text = listingDataItem.subReddit
                    textViewHolder.subRedditName.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                    textViewHolder.title.text = listingDataItem.title

                    textViewHolder.subRedditName.setOnClickListener {
                        SUBREDDIT.link = listingDataItem.subReddit
                        onClickListener(SUBREDDIT)
                    }

                    textViewHolder.itemView.setOnClickListener {
                        POST.link = listingDataItem.permalink
                        onClickListener(POST)
                    }
                    textViewHolder.selfText.text =
                        (listingDataItem.cardType as CardType.TextCard).selfText
                }
                is CardType.ImageCard -> {
                    val imageViewHolder = holder as ImageViewHolder
                    imageViewHolder.subRedditName.text = listingDataItem.subReddit
                    imageViewHolder.subRedditName.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                    imageViewHolder.title.text = listingDataItem.title

                    imageViewHolder.subRedditName.setOnClickListener {
                        SUBREDDIT.link = listingDataItem.subReddit
                        onClickListener(SUBREDDIT)
                    }

                    imageViewHolder.itemView.setOnClickListener {
                        POST.link = listingDataItem.permalink
                        onClickListener(POST)
                    }
                    Glide.with(imageViewHolder.itemView.context)
                        .load((listingDataItem.cardType as CardType.ImageCard).thumbnailUrl)
                        .into(imageViewHolder.thumbnailImage)
                }
                is CardType.UrlCard -> {
                    val urlViewHolder = holder as URLViewHolder
                    urlViewHolder.subRedditName.text = listingDataItem.subReddit
                    urlViewHolder.subRedditName.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                    urlViewHolder.title.text = listingDataItem.title

                    urlViewHolder.subRedditName.setOnClickListener {
                        SUBREDDIT.link = listingDataItem.subReddit
                        onClickListener(SUBREDDIT)
                    }

                    urlViewHolder.itemView.setOnClickListener {
                        POST.link = listingDataItem.permalink
                        onClickListener(POST)
                    }
                    urlViewHolder.textUrl.text =
                        (listingDataItem.cardType as CardType.UrlCard).urlOverriddenByDest
                }
                is CardType.TitleCard -> {
                    val textViewHolder = holder as TextViewHolder
                    textViewHolder.subRedditName.text = listingDataItem.subReddit
                    textViewHolder.subRedditName.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                    textViewHolder.title.text = listingDataItem.title

                    textViewHolder.subRedditName.setOnClickListener {
                        SUBREDDIT.link = listingDataItem.subReddit
                        onClickListener(SUBREDDIT)
                    }

                    textViewHolder.itemView.setOnClickListener {
                        POST.link = listingDataItem.permalink
                        onClickListener(POST)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        return when (viewType) {
            ViewType.TEXT_CARD.ordinal -> TextViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.text_type_list_item, parent, false)
            )

            ViewType.IMAGE_CARD.ordinal -> ImageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.image_type_list_item, parent, false)
            )

            ViewType.URL_CARD.ordinal -> URLViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.url_type_list_item, parent, false)
            )

            else -> TextViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.text_type_list_item, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)!!
        return when (item.cardType) {
            is CardType.TextCard -> ViewType.TEXT_CARD.ordinal
            is CardType.ImageCard -> ViewType.IMAGE_CARD.ordinal
            is CardType.UrlCard -> ViewType.URL_CARD.ordinal
            CardType.TitleCard -> ViewType.TEXT_CARD.ordinal
        }
    }
}

abstract class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class ImageViewHolder(itemView: View) : ChildViewHolder(itemView) {
    val subRedditName: TextView = itemView.findViewById(R.id.subreddit_name)
    val title: TextView = itemView.findViewById(R.id.title_name)
    val thumbnailImage: ImageView = itemView.findViewById(R.id.thumbnail_image)
}

class TextViewHolder(itemView: View) : ChildViewHolder(itemView) {
    val subRedditName: TextView = itemView.findViewById(R.id.subreddit_name_text)
    val title: TextView = itemView.findViewById(R.id.title_name_text)
    val selfText: TextView = itemView.findViewById(R.id.self_text)
}

class URLViewHolder(itemView: View) : ChildViewHolder(itemView) {
    val subRedditName: TextView = itemView.findViewById(R.id.subreddit_name_url)
    val title: TextView = itemView.findViewById(R.id.title_name_url)
    val textUrl: TextView = itemView.findViewById(R.id.text_url)
}

enum class Click(var link: String) {
    SUBREDDIT(""),
    POST("")
}

enum class ViewType {
    TEXT_CARD, IMAGE_CARD, URL_CARD
}

sealed class CardType {
    data class TextCard(val selfText: String) : CardType()
    data class ImageCard(val thumbnailUrl: String) : CardType()
    data class UrlCard(
        val urlOverriddenByDest: String
    ) : CardType()

    object TitleCard : CardType()
}