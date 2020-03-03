package pl.dzielins42.newsapi.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import pl.dzielins42.newsapi.data.model.News
import pl.dzielins42.newsapi.data.NewsRepository
import timber.log.Timber

class NewsListViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val newsList: LiveData<List<News>>
        get() = newsListMutableLiveData
    private val newsListMutableLiveData = MutableLiveData<List<News>>()

    fun loadDataPage(page: Int) {
        val loadDataPageAction = newsRepository.getData(
            country = NewsRepository.COUNTRY_US,
            categories = listOf(NewsRepository.CATEGORY_SPORTS, NewsRepository.CATEGORY_BUSINESS),
            page = page
        )

        compositeDisposable.add(loadDataPageAction
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { newData ->
                    val oldData = newsListMutableLiveData.value ?: emptyList()
                    newsListMutableLiveData.value = oldData.plus(newData)
                },
                { error ->
                    Timber.e(error)
                }
            )
        )
    }


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}