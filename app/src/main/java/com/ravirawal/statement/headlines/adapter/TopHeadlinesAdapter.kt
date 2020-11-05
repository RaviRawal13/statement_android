package com.ravirawal.statement.headlines.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ravirawal.statement.R
import com.ravirawal.statement.databinding.ArticleLargeRowBinding
import com.ravirawal.statement.model.TopHeadlines
import com.ravirawal.statement.util.default
import com.ravirawal.statement.util.loadImage
import com.ravirawal.statement.util.toRelativeDate

private class DiffCallback : DiffUtil.ItemCallback<TopHeadlines>() {
    override fun areItemsTheSame(
        oldItem: TopHeadlines,
        newItem: TopHeadlines
    ): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(
        oldItem: TopHeadlines,
        newItem: TopHeadlines
    ): Boolean {
        return oldItem.description == newItem.description &&
                oldItem.title == newItem.title
    }
}

class TopHeadlinesAdapter(
    private val clickListener: (view: View, ivContent: ImageView, tvHeadline: TextView, tvContent: TextView, tvDate: TextView, position: Int, sourcesItem: TopHeadlines?) -> Unit = { _, _, _, _, _, _, _ -> run {} }
) :
    PagedListAdapter<TopHeadlines, TopHeadlinesAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(
        private val viewBinding: ArticleLargeRowBinding,
        private val clickListener: (view: View, ivContent: ImageView, tvHeadline: TextView, tvContent: TextView, tvDate: TextView, position: Int, sourcesItem: TopHeadlines?) -> Unit = { _, _, _, _, _, _, _ -> run {} }
    ) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: TopHeadlines?) {
            with(viewBinding) {
                val urlToImage = item?.urlToImage.default()
                ivContentImage.loadImage(urlToImage)
                ivContentImage.transitionName = urlToImage

                val title = item?.title.default()
                tvHeadline.text = title
                tvHeadline.transitionName = title

                val content = item?.content.default()
                tvContent.text = content
                tvContent.transitionName = content

                val dateText = "${item?.author?.default()} - ${item?.publishedAt.toRelativeDate()}"
                tvDate.text = dateText
                tvDate.transitionName = dateText

                if (item?.url.isNullOrEmpty()) {
                    bSource.visibility = View.GONE
                } else {
                    bSource.visibility = View.VISIBLE
                    val uri = Uri.parse(item?.url)
                    if (uri != null)
                        bSource.text = "Go to ${uri.host}"
                }

                bSource.setOnClickListener {
                    clickListener(
                        it,
                        ivContentImage,
                        tvHeadline,
                        tvContent,
                        tvDate,
                        adapterPosition,
                        item
                    )
                }

                ivShare.setOnClickListener {
                    clickListener(
                        it, ivContentImage,
                        tvHeadline,
                        tvContent,
                        tvDate, adapterPosition, item
                    )
                }

                root.setOnClickListener {
                    clickListener(
                        it, ivContentImage,
                        tvHeadline,
                        tvContent,
                        tvDate, adapterPosition, item
                    )
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article: TopHeadlines? = getItem(position)
        holder.bind(article)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ArticleLargeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        clickListener
    )

    override fun getItemViewType(position: Int): Int {
        return position
    }
}