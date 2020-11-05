package com.ravirawal.statement.home.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ravirawal.statement.databinding.ArticleFullPageRowBinding
import com.ravirawal.statement.model.Article
import com.ravirawal.statement.model.NewsResponse
import com.ravirawal.statement.util.default
import com.ravirawal.statement.util.loadImage
import com.ravirawal.statement.util.toRelativeDate

private class DiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(
        oldItem: Article,
        newItem: Article
    ): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(
        oldItem: Article,
        newItem: Article
    ): Boolean {
        return oldItem.description == newItem.description &&
                oldItem.title == newItem.title
    }
}

class ExploreAdapter(
    private val context: Context,
    private val clickListener: (view: View, position: Int, sourcesItem: Article?) -> Unit = { _, _, _ -> run {} }
) :
    ListAdapter<Article, ExploreAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(
        private val viewBinding: ArticleFullPageRowBinding,
        private val clickListener: (view: View, position: Int, sourcesItem: Article?) -> Unit = { _, _, _ -> run {} }
    ) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: Article?) {
            with(viewBinding) {
                ivContentImage.loadImage(item?.urlToImage.default())
                tvAuthor.text = item?.author.default()
                tvHeadline.text = item?.title.default()
                tvContent.text = item?.content.default()
                tvDate.text = item?.publishedAt.toRelativeDate()

                if (item?.url.isNullOrEmpty()) {
                    bSource.visibility = View.GONE
                } else {
                    bSource.visibility = View.VISIBLE
                    val uri = Uri.parse(item?.url)
                    if (uri != null)
                        bSource.text = "Go to ${uri.host}"
                }


                bSource.setOnClickListener {
                    clickListener(it, adapterPosition, item)
                }

                ivShare.setOnClickListener {
                    clickListener(it, adapterPosition, item)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article: Article? = getItem(position)
        holder.bind(article)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ArticleFullPageRowBinding.inflate(LayoutInflater.from(context), parent, false),
        clickListener
    )

    override fun getItemViewType(position: Int): Int {
        return position
    }
}