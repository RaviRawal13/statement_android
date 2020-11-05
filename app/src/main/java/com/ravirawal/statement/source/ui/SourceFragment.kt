package com.ravirawal.statement.source.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ravirawal.statement.R
import com.ravirawal.statement.base.BaseFragment
import com.ravirawal.statement.databinding.LoadingShimmerFullPageLayoutBinding
import com.ravirawal.statement.databinding.LoadingShimmerSourcesLayoutBinding
import com.ravirawal.statement.databinding.SourcesFragmentBinding
import com.ravirawal.statement.model.SourcesItem
import com.ravirawal.statement.source.adapter.SourceAdapter
import com.ravirawal.statement.source.vm.SourcesViewModel
import com.ravirawal.statement.util.ARG_SOURCE
import com.ravirawal.statement.util.ARG_URL
import com.ravirawal.statement.util.isOnline

class SourceFragment : BaseFragment<SourcesViewModel, SourcesFragmentBinding>() {

    private val sourcesViewModel by viewModels<SourcesViewModel> { viewModelFactory }

    private val loading by lazy {
        LoadingShimmerSourcesLayoutBinding.inflate(
            LayoutInflater.from(viewBinding.root.context),
            viewBinding.root
        )
    }

    private val sourceAdapter =
        SourceAdapter { v: View, _: Int, i: SourcesItem? -> onRecyclerItemClicked(v, i) }

    private fun onRecyclerItemClicked(v: View, i: SourcesItem?) {
        when (v.id) {
            R.id.b_source -> {
                findNavController().navigate(
                    R.id.action_sourceFragment_to_webViewFragment,
                    Bundle().apply {
                        putString(ARG_URL, i?.url ?: "")
                    })
            }
            R.id.cv_source -> {
                findNavController().navigate(
                    R.id.action_sourceFragment_to_topHeadlinesFragment,
                    Bundle().apply {
                        putString(ARG_SOURCE, i?.id ?: "")
                    })
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = SourcesFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            setUpRecyclerView()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sourcesViewModel.fetchSources(context?.isOnline() ?: false)
            .observe(viewLifecycleOwner) { output ->
                onOutput(output, onSuccess = {
                    stopLoading()
                    viewBinding.groupContentSources.visibility = View.VISIBLE
                    sourceAdapter.submitList(it)
                }, onFailure = {
                    stopLoading()
                    viewBinding.groupContentSources.visibility = View.GONE
                }, onLoad = {
                    startLoading()
                })
            }
    }


    private fun startLoading() {
        with(loading.shimmerSources) {
            startShimmer()
            visibility = View.VISIBLE
        }
    }

    private fun stopLoading() {
        with(loading.shimmerSources) {
            visibility = View.GONE
            stopShimmer()
        }
    }

    private fun setUpRecyclerView() {
        with(viewBinding) {
            rvSources.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvSources.adapter = sourceAdapter
        }
    }
}