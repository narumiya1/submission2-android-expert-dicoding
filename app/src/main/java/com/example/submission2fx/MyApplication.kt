package com.example.submission2fx

import android.app.Application
import com.example.submission2fx.core.dinjct.databaseModule
import com.example.submission2fx.core.dinjct.networkModule
import com.example.submission2fx.core.dinjct.repositoryModule
import com.example.submission2fx.dinjct.useCaseModule
import com.example.submission2fx.dinjct.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.koinApplication


@FlowPreview
@ExperimentalCoroutinesApi
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
       startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }

    }
}