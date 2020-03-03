package pl.dzielins42.newsapi.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pl.dzielins42.newsapi.Const
import pl.dzielins42.newsapi.data.model.News
import java.util.concurrent.TimeUnit

class NewsRepository {

    fun getData(page: Int): Single<List<News>> {
        return Single.fromCallable {
            getMockData(page * Const.PAGE_SIZE, Const.PAGE_SIZE)
        }.subscribeOn(Schedulers.io()).delay(1, TimeUnit.SECONDS)
    }

    private fun getMockData(startIndex: Int, count: Int): List<News> {
        return ArrayList<News>().apply {
            for (i in 0 until count) {
                add(News("news #${i + startIndex}"))
            }
        }
    }
}