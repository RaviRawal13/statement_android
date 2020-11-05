package com.ravirawal.statement.splash.vm

import androidx.lifecycle.*
import com.ravirawal.statement.base.BaseViewModel
import com.ravirawal.statement.model.Article
import com.ravirawal.statement.model.NewsResponse
import com.ravirawal.statement.network.Output
import com.ravirawal.statement.repository.NewsRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    fun clearExploreCache() {
        GlobalScope.launch {
            newsRepository.clearExploreCache()
        }
    }

}