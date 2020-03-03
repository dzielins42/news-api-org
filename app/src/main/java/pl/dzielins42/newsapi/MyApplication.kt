package pl.dzielins42.newsapi

import android.app.Application
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import pl.dzielins42.newsapi.data.NewsRepository
import pl.dzielins42.newsapi.data.NewsApiRestService
import pl.dzielins42.newsapi.view.NewsListViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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
                // Data
                module {
                    fun provideNewsApiRestService(
                        retrofit: Retrofit
                    ): NewsApiRestService {
                        return retrofit.create(NewsApiRestService::class.java)
                    }

                    single {
                        OkHttpClient.Builder()
                            // Interceptor to add API key in header
                            .addInterceptor { chain ->
                                val newRequest = chain.request().newBuilder()
                                    .addHeader(HEADER_NEWS_API_KEY, BuildConfig.NEWS_API_KEY)
                                    .build()
                                chain.proceed(newRequest)
                            }
                            // Interceptor to log whether response is from cache or network
                            .addInterceptor { chain ->
                                val response = chain.proceed(chain.request())
                                val url = chain.request().url().toString()
                                when {
                                    response.cacheResponse() != null -> Timber.d("$url from cache")
                                    response.networkResponse() != null -> Timber.d("$url from network")
                                }
                                response
                            }
                            .cache(
                                Cache(
                                    this@MyApplication.cacheDir,
                                    OKHTTP_CACHE_SIZE.toLong()
                                )
                            )
                            .build()
                    }

                    single {
                        Retrofit.Builder()
                            .baseUrl(BuildConfig.NEWS_API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(
                                RxJava2CallAdapterFactory.createWithScheduler(
                                    Schedulers.io()
                                )
                            )
                            .client(get())
                            .build()
                    }
                    single { provideNewsApiRestService(get()) }
                    single { NewsRepository(get()) }
                }
            ))
        }
    }

    companion object {
        private const val HEADER_NEWS_API_KEY = "X-Api-Key"
        private const val OKHTTP_CACHE_SIZE = 1 * 1024 * 1024 // 1MB
    }
}