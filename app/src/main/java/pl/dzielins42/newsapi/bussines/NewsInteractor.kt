package pl.dzielins42.newsapi.bussines

import io.reactivex.Single
import pl.dzielins42.newsapi.data.NewsRepository
import pl.dzielins42.newsapi.data.model.News

class NewsInteractor(
    private val newsRepository: NewsRepository
) {

    fun getData(page: Int): Single<List<News>> {
        return newsRepository.getData(page)
    }
}