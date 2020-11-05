package com.ravirawal.statement.di.activity

import com.ravirawal.statement.SplashActivity
import com.ravirawal.statement.splash.ui.SplashFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @Binds
    abstract fun bindActivity(activity: SplashActivity): SplashActivity
}