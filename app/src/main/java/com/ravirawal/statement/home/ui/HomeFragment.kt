package com.ravirawal.statement.home.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ravirawal.statement.R
import com.ravirawal.statement.base.BaseFragment
import com.ravirawal.statement.databinding.HomeFragmentBinding
import com.ravirawal.statement.home.adapter.ExploreAdapter
import com.ravirawal.statement.home.vm.HomeViewModel
import com.ravirawal.statement.model.Article
import com.ravirawal.statement.model.NewsResponse
import com.ravirawal.statement.network.Output
import com.ravirawal.statement.util.*
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }

    private val exploreAdapter: ExploreAdapter? by lazy {
        context?.let {
            ExploreAdapter(it) { v: View, _: Int, i: Article? ->
                onRecyclerItemClicked(v, i)
            }
        }
    }

    private fun onRecyclerItemClicked(v: View, article: Article?) {
        when (v.id) {
            R.id.b_source -> {
                findNavController().navigate(
                    R.id.action_homeFragment_to_webViewFragment,
                    Bundle().apply {
                        putString(ARG_URL, article?.url ?: "")
                    })
            }
            R.id.iv_share -> {
                share(article?.title ?: "", article?.url ?: "", context)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = HomeFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            setUpViewPager()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel.fetchNews("india", context?.isOnline() ?: false)
            .observe(viewLifecycleOwner) { output: Output<List<Article>?> ->
                onOutput(output, onSuccess = {
                    sharedPreferences.edit {
                        putBoolean(PREF_IS_DATA_LOADED, true)
                        putLong(PREF_LAST_HOME_SYNC, System.currentTimeMillis())
                    }
                    viewBinding.viewPagerHome.visibility = View.VISIBLE
                    exploreAdapter?.submitList(it)
                }, onFailure = {
                    viewBinding.viewPagerHome.visibility = View.GONE
                })
            }
    }

    private fun setUpViewPager() {
        with(viewBinding.viewPagerHome) {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            currentItem = homeViewModel.exploreScrollPosition
            adapter = exploreAdapter

        }
    }

}