package com.zestworks.myriadforreddit.feature.postDetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.myriadforreddit.R
import com.zestworks.myriadforreddit.data.postDetail.PostDetailUIData


class ListingPostDetailAdapter(itemCallback: DiffUtil.ItemCallback<PostDetailUIData>) :
    PagingDataAdapter<PostDetailUIData, PostDetailViewHolder>(itemCallback) {
    override fun onBindViewHolder(holder: PostDetailViewHolder, position: Int) {
        val item = getItem(position)
        holder.user.text = item!!.authorName
        holder.title.text = item.message
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDetailViewHolder {
        return PostDetailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_detail_item, parent, false)
        )
    }
}

class PostDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val user: TextView = itemView.findViewById(R.id.user_name)
    val title: TextView = itemView.findViewById(R.id.title_name)
}
