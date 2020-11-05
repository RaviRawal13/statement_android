package com.ravirawal.statement.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<VB : ViewBinding, ITEM : Any?>(itemView: VB) : RecyclerView.ViewHolder(itemView.root) {
    abstract fun bind(item: ITEM)
}
