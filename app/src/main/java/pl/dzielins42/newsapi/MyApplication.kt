package pl.dzielins42.newsapi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import pl.dzielins42.newsapi.data.NewsRepository
import pl.dzielins42.newsapi.bussines.NewsInteractor
import pl.dzielins42.newsapi.view.NewsListViewModel
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            androidContext(this@MyApplication)
            modules(listOf(
                // ViewModels
                module { viewModel { NewsListViewModel(get()) } },
                // Business logic
                module { single { NewsInteractor(get()) } },
                // Data
                module { single { NewsRepository() } }
            ))
        }
    }
}