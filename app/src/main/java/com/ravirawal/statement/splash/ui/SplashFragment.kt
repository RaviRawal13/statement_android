package com.ravirawal.statement.splash.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ravirawal.statement.MainActivity
import com.ravirawal.statement.R
import com.ravirawal.statement.base.BaseFragment
import com.ravirawal.statement.databinding.SplashFragmentBinding
import com.ravirawal.statement.splash.vm.SplashViewModel
import com.ravirawal.statement.util.PREF_IS_DATA_LOADED
import com.ravirawal.statement.util.PREF_LAST_HOME_SYNC
import com.ravirawal.statement.util.isOnline
import com.ravirawal.statement.util.load
import kotlinx.coroutines.delay
import javax.inject.Inject


private const val DELAY = 3000L
private const val DAY = 24 * 60 * 60 * 1000.toLong()

class SplashFragment : BaseFragment<SplashViewModel, SplashFragmentBinding>() {

    private val splashViewModel by viewModels<SplashViewModel> { viewModelFactory }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = SplashFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideLoader()
        viewBinding.ivSplash.load(R.drawable.splash_icon)
        this.lifecycleScope.launchWhenResumed {
            val isDataLoaded = sharedPreferences.getBoolean(PREF_IS_DATA_LOADED, false)
            if (isDataLoaded && context?.isOnline() == true) {
                val lastSync = sharedPreferences.getLong(PREF_LAST_HOME_SYNC, 0L)
                if (lastSync > (System.currentTimeMillis() - DAY)) {
                    splashViewModel.clearExploreCache()
                }
            }
            delay(DELAY)
            if (context?.isOnline() == true || isDataLoaded) {
                launchMain()
            } else {
                viewBinding.ivSplash.visibility = View.GONE
                showNoInternet()
            }
        }
    }

    private fun launchMain() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        activity?.finish()
    }
}