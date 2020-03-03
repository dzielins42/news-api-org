package pl.dzielins42.newsapi.data

import io.reactivex.Single
import pl.dzielins42.newsapi.Const
import pl.dzielins42.newsapi.data.model.News
import timber.log.Timber

class NewsRepository(
    private val newsApiRestService: NewsApiRestService
) {

    fun getData(page: Int): Single<List<News>> {
        // Just to test
        return newsApiRestService.getSources("sports", "en")
            .doOnSuccess { Timber.d(it.toString()) }
            .map { getMockData(page * Const.PAGE_SIZE, Const.PAGE_SIZE) }
    }

    private fun getMockData(startIndex: Int, count: Int): List<News> {
        return ArrayList<News>().apply {
            for (i in 0 until count) {
                add(News("news #${i + startIndex}"))
            }
        }
    }
}