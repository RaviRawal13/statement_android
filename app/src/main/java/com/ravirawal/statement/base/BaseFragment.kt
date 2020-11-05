package com.ravirawal.statement.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ravirawal.statement.R
import com.ravirawal.statement.databinding.EmptyLayoutBinding
import com.ravirawal.statement.databinding.LoadingLayoutBinding
import com.ravirawal.statement.databinding.NoInternetLayoutBinding
import com.ravirawal.statement.databinding.SplashFragmentBinding
import com.ravirawal.statement.model.NewsResponse
import com.ravirawal.statement.network.Output
import com.ravirawal.statement.util.act
import com.ravirawal.statement.util.load
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

open class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {

    open lateinit var viewModel: VM

    lateinit var viewBinding: VB

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val loadingLayoutBinding: LoadingLayoutBinding? by lazy {
        this.context?.let { con ->
            (viewBinding.root as? ViewGroup)?.let {
                LoadingLayoutBinding.inflate(
                    LayoutInflater.from(con),
                    it
                )
            }
        }
    }

    private val noInternetLayoutBinding: NoInternetLayoutBinding? by lazy {
        this.context?.let { con ->
            (viewBinding.root as? ViewGroup)?.let {
                NoInternetLayoutBinding.inflate(
                    LayoutInflater.from(con),
                    it
                )
            }
        }
    }

    private val noDataLayoutBinding: EmptyLayoutBinding? by lazy {
        this.context?.let { con ->
            (viewBinding.root as? ViewGroup)?.let {
                EmptyLayoutBinding.inflate(
                    LayoutInflater.from(con),
                    it
                )
            }
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLoader()
    }

    fun <RES : Any?> onOutput(
        outPut: Output<RES>, onSuccess: ((RES) -> Unit)? = null,
        onFailure: (() -> Unit)? = null, onLoad: (() -> Unit)? = null
    ) {
        when (outPut) {
            is Output.LOAD -> {
                showLoader()
                onLoad?.invoke()
            }
            is Output.Success -> {
                hideLoader()

                onSuccess?.invoke(outPut.data)
            }
            else -> {
                hideLoader()
                showNoData()
                onFailure?.invoke()
            }
        }
    }

    private fun setLoader() {
        loadingLayoutBinding?.ivLoading?.load(R.drawable.loading)
    }

    fun showLoader() {
        loadingLayoutBinding?.ivLoading?.visibility = View.VISIBLE
    }

    fun hideLoader() {
        loadingLayoutBinding?.ivLoading?.visibility = View.GONE
    }

    fun showNoInternet() {
        noInternetLayoutBinding?.groupNoInternet?.visibility = View.VISIBLE
    }

    fun hideNoInternet() {
        noInternetLayoutBinding?.groupNoInternet?.visibility = View.GONE
    }

    fun showNoData() {
        noDataLayoutBinding?.groupEmptyLayout?.visibility = View.VISIBLE
    }

    fun hideNoData() {
        noDataLayoutBinding?.groupEmptyLayout?.visibility = View.GONE
    }
}