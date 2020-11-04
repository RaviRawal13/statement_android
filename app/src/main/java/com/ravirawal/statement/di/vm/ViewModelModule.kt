package com.ravirawal.statement.di.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ravirawal.statement.base.BaseViewModel
import com.ravirawal.statement.headlines.vm.TopHeadlinesViewModel
import com.ravirawal.statement.home.vm.HomeViewModel
import com.ravirawal.statement.source.vm.SourcesViewModel
import com.ravirawal.statement.splash.vm.SplashViewModel
import com.ravirawal.statement.util.StatementViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SourcesViewModel::class)
    abstract fun bindSourceViewModel(viewModel: SourcesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopHeadlinesViewModel::class)
    abstract fun bindTopHeadlinesModel(viewModel: TopHeadlinesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: StatementViewModelFactory): ViewModelProvider.Factory
}
