package com.zestworks.myriadforreddit.feature.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.myriadforreddit.R


class PostPagingDataAdapter(itemCallback: DiffUtil.ItemCallback<PostDetailUIDataItem>) :
    PagingDataAdapter<PostDetailUIDataItem, PostDetailViewHolder>(itemCallback) {
    override fun onBindViewHolder(holder: PostDetailViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is CommentViewHolder -> {
                holder.commentText.text = item!!.message
                holder.userName.text = item.authorName
            }
            is TitleViewHolder -> {
                holder.user.text = item!!.authorName
                holder.title.text = item.message
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDetailViewHolder {
        if (viewType == 1) {
            return CommentViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.comment_list_item, parent, false)
            )
        }
        return TitleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_detail_item, parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return item?.messageType?.ordinal ?: -1
    }
}

class CommentViewHolder(itemView: View) : PostDetailViewHolder(itemView) {
    val userName: TextView = itemView.findViewById(R.id.user_name)
    val commentText: TextView = itemView.findViewById(R.id.comment_text)
}

class TitleViewHolder(itemView: View) : PostDetailViewHolder(itemView) {
    val user: TextView = itemView.findViewById(R.id.user_name)
    val title: TextView = itemView.findViewById(R.id.title_name)
}

abstract class PostDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)