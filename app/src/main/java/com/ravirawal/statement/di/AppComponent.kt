@file:Suppress("unused")

package com.ravirawal.statement.di

import com.ravirawal.statement.StatementApp
import com.ravirawal.statement.di.activity.ActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: StatementApp): Builder

        fun build(): AppComponent
    }

    fun inject(application: StatementApp)
}