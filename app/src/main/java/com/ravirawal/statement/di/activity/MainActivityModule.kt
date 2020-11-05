package com.ravirawal.statement.di.activity

import androidx.appcompat.app.AppCompatActivity
import com.ravirawal.statement.MainActivity
import com.ravirawal.statement.article_detail.ui.ArticleFragment
import com.ravirawal.statement.headlines.ui.TopHeadlinesFragment
import com.ravirawal.statement.home.ui.HomeFragment
import com.ravirawal.statement.source.ui.SourceFragment
import com.ravirawal.statement.webview.WebViewFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeWebViewFragment(): WebViewFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeSourceFragment(): SourceFragment

    @ContributesAndroidInjector
    abstract fun contributeArticleFragment(): ArticleFragment

    @ContributesAndroidInjector
    abstract fun contributeTopHeadlinesFragment(): TopHeadlinesFragment

    @Binds
    abstract fun bindActivity(activity: MainActivity): AppCompatActivity

}