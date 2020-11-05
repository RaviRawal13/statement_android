package com.ravirawal.statement.webview

import android.graphics.Bitmap
import android.net.MailTo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.ravirawal.statement.base.BaseFragment
import com.ravirawal.statement.databinding.WebviewFragmentBinding
import com.ravirawal.statement.source.vm.SourcesViewModel
import com.ravirawal.statement.util.ARG_URL

class WebViewFragment : BaseFragment<SourcesViewModel, WebviewFragmentBinding>() {

    val url: String by lazy { arguments?.getString(ARG_URL, "") ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = WebviewFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            if (url.isEmpty()) {
                showNoData()
            } else {
                showLoader()

                viewBinding.wvOpenUrl.apply {
                    this.settings.pluginState = WebSettings.PluginState.ON
                    this.settings.javaScriptEnabled = true
                    this.settings.javaScriptCanOpenWindowsAutomatically = true
                    this.settings.setSupportMultipleWindows(false)
                    this.settings.domStorageEnabled = true
                    this.overScrollMode = WebView.OVER_SCROLL_NEVER
                    this.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                            view.loadUrl(url)
                            return true
                        }

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            showLoader()
                            super.onPageStarted(view, url, favicon)
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            hideLoader()
                            super.onPageFinished(view, url)
                        }
                    }
                    this.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    this.settings.mediaPlaybackRequiresUserGesture = true
                    this.webChromeClient = WebChromeClient()
                    this.isClickable = true
                    this.loadUrl(this@WebViewFragment.url)
                }
            }
        }
    }
}