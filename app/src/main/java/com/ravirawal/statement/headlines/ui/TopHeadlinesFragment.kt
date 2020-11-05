package com.ravirawal.statement.headlines.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ravirawal.statement.R
import com.ravirawal.statement.base.BaseFragment
import com.ravirawal.statement.databinding.TopHeadlinesFragmentBinding
import com.ravirawal.statement.headlines.adapter.TopHeadlinesAdapter
import com.ravirawal.statement.headlines.vm.TopHeadlinesViewModel
import com.ravirawal.statement.model.TopHeadlines
import com.ravirawal.statement.network.Output
import com.ravirawal.statement.util.*


class TopHeadlinesFragment : BaseFragment<TopHeadlinesViewModel, TopHeadlinesFragmentBinding>() {

    private val source: String by lazy { arguments?.getString(ARG_SOURCE, "") ?: "" }
    private val topHeadlinesViewModel by viewModels<TopHeadlinesViewModel> { viewModelFactory }

    private var fabOpen: Animation? = null

    private var fabClose: Animation? = null

    private val topHeadlinesAdapter =
        TopHeadlinesAdapter { v: View, ivContent: ImageView, tvHeadline: TextView, tvContent: TextView, tvDate: TextView, _: Int, i: TopHeadlines? ->
            onRecyclerItemClicked(
                v, ivContent, tvHeadline, tvContent, tvDate,
                i
            )
        }

    private fun onRecyclerItemClicked(
        v: View,
        ivContent: ImageView,
        tvHeadline: TextView,
        tvContent: TextView,
        tvDate: TextView,
        topHeadlines: TopHeadlines?
    ) {
        when (v.id) {
            R.id.b_source -> {
                findNavController().navigate(
                    R.id.action_topHeadlinesFragment_to_webViewFragment,
                    Bundle().apply {
                        putString(ARG_URL, topHeadlines?.url ?: "")
                    })
            }
            R.id.iv_share -> {
                share(topHeadlines?.title ?: "", topHeadlines?.url ?: "", context)
            }
            R.id.cv_content_large -> {
                val extras = FragmentNavigatorExtras(
                    ivContent to ivContent.transitionName,
                    tvHeadline to tvHeadline.transitionName,
                    tvContent to tvContent.transitionName,
                    tvDate to tvDate.transitionName
                    )
                val dir =
                    TopHeadlinesFragmentDirections.actionTopHeadlinesFragmentToArticleFragment(
                        topHeadlines
                    )
                findNavController().navigate(dir, extras)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = TopHeadlinesFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            setUpRecyclerView()
            fabClose = AnimationUtils.loadAnimation(
                context,
                R.anim.fab_close
            )
            fabOpen = AnimationUtils.loadAnimation(
                context,
                R.anim.fab_open
            )
            setupFab()
        }
    }

    private var showSorting = false
    private fun setupFab() {
        with(viewBinding) {
            fabSorting.setOnClickListener {
                if (showSorting) {
                    hideFabs(viewBinding)
                } else {
                    showFabs(viewBinding)
                }
            }

            fabAscending.setOnClickListener {
                topHeadlinesViewModel.onSortBy(true)

                hideFabs(viewBinding)
            }
            fabDescending.setOnClickListener {
                topHeadlinesViewModel.onSortBy(false)

                hideFabs(viewBinding)
            }
        }
    }

    private fun showFabs(viewBinding: TopHeadlinesFragmentBinding) = with(viewBinding) {
        groupSorting.visibility = View.VISIBLE
        fabAscending.startAnimation(fabOpen)
        fabDescending.startAnimation(fabOpen)
        showSorting = true
    }

    private fun hideFabs(viewBinding: TopHeadlinesFragmentBinding) = with(viewBinding) {
        groupSorting.visibility = View.GONE
        fabAscending.startAnimation(fabClose)
        fabDescending.startAnimation(fabClose)
        showSorting = false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showLoader()
        val data = topHeadlinesViewModel.newsList(
            context?.isOnline() ?: false,
            source
        )
        data.observe(viewLifecycleOwner) {
            hideLoader()
            topHeadlinesAdapter.submitList(it)
        }
    }

    private fun setUpRecyclerView() {
        with(viewBinding) {
            rvTopHeadlines.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvTopHeadlines.adapter = topHeadlinesAdapter
        }
    }
}