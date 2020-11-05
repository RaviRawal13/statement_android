package com.ravirawal.statement.source.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ravirawal.statement.base.BaseViewHolder
import com.ravirawal.statement.databinding.SourcesRowBinding
import com.ravirawal.statement.model.SourcesItem
import com.ravirawal.statement.util.default

private class DiffCallback : DiffUtil.ItemCallback<SourcesItem>() {
    override fun areItemsTheSame(
        oldItem: SourcesItem,
        newItem: SourcesItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SourcesItem,
        newItem: SourcesItem
    ): Boolean {
        return oldItem.description == newItem.description &&
                oldItem.name == newItem.name &&
                oldItem.url == newItem.url
    }
}

class SourceAdapter(private val clickListener: (view: View, position: Int, sourcesItem: SourcesItem?) -> Unit = { _, _, _ -> run {} }) :
    ListAdapter<SourcesItem, SourceAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(
        private val viewBinding: SourcesRowBinding,
        private val clickListener: (view: View, position: Int, sourcesItem: SourcesItem?) -> Unit = { _, _, _ -> run {} }
    ) :
        BaseViewHolder<SourcesRowBinding, SourcesItem?>(viewBinding) {
        override fun bind(item: SourcesItem?) {
            with(viewBinding) {
                tvHeadline.text = item?.name.default()
                tvContent.text = item?.description.default()
                bSource.text = "Go to ${item?.url}"

                bSource.setOnClickListener {
                    clickListener(it, adapterPosition, item)
                }

                root.setOnClickListener {
                    clickListener(it, adapterPosition, item)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article: SourcesItem? = getItem(position)
        holder.bind(article)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        SourcesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        clickListener
    )

    override fun getItemViewType(position: Int): Int {
        return position
    }
}