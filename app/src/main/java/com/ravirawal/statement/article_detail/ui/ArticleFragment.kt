package com.ravirawal.statement.article_detail.ui

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.ravirawal.statement.R
import com.ravirawal.statement.base.BaseFragment
import com.ravirawal.statement.databinding.ArticleDetailFragmentBinding
import com.ravirawal.statement.home.vm.HomeViewModel
import com.ravirawal.statement.model.TopHeadlines
import com.ravirawal.statement.util.*

class ArticleFragment : BaseFragment<HomeViewModel, ArticleDetailFragmentBinding>() {

    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = ArticleDetailFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArticleData(args.topHeadline)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move)

        viewBinding.bSource.setOnClickListener {
            findNavController().navigate(
                R.id.action_articleFragment_to_webViewFragment,
                Bundle().apply {
                    putString(ARG_URL, args.topHeadline?.url ?: "")
                })
        }
    }

    private fun initArticleData(topHeadline: TopHeadlines?) {
        if (topHeadline == null) {
            viewBinding.groupContentArticle.visibility = View.GONE
            showNoData()
//            findNavController().navigateUp()
        } else {
            viewBinding.groupContentArticle.visibility = View.VISIBLE
            hideNoData()
        }
        viewBinding.apply {
            val urlToImage = topHeadline?.urlToImage.default()
            ivContentImage.loadImage(topHeadline?.urlToImage.default())
            ivContentImage.transitionName = urlToImage

            val title = topHeadline?.title.default()
            tvHeadline.text = title
            tvHeadline.transitionName = title

            val dateAndAuthor =
                "${topHeadline?.author?.default()} - ${topHeadline?.publishedAt.toRelativeDate()}"
            tvDate.text = dateAndAuthor
            tvDate.transitionName = dateAndAuthor

            val content = topHeadline?.content.default()
            tvContent.text = content
            tvContent.transitionName = content

            if (topHeadline?.url.isNullOrEmpty()) {
                bSource.visibility = View.GONE
            } else {
                bSource.visibility = View.VISIBLE
                val uri = Uri.parse(topHeadline?.url)
                if (uri != null)
                    bSource.text = "Go to ${uri.host}"
            }
        }

    }
}