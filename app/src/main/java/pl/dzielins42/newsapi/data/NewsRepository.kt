package pl.dzielins42.newsapi.data

import android.text.TextUtils
import io.reactivex.Single
import pl.dzielins42.newsapi.Const
import pl.dzielins42.newsapi.data.model.News
import pl.dzielins42.newsapi.data.model.api.SourcesResponse
import timber.log.Timber

class NewsRepository(
    private val newsApiRestService: NewsApiRestService
) {

    fun getData(
        categories: List<String>,
        language: String = "",
        country: String = "",
        page: Int
    ): Single<List<News>> {
        val getSourceIds = Single.zip(
            categories.map { getSourcesForCategory(it, language, country) }
        ) { responses ->
            retrieveSourceIdsFromSourcesResponses(responses)
        }

        return getSourceIds.flatMap { sources ->
            val formattedSources = TextUtils.join(",", sources)
            newsApiRestService.getTopHeadlines(
                country = "",
                category = "",
                sources = formattedSources,
                query = "",
                pageSize = Const.PAGE_SIZE,
                // NewsAPI.org counts pages from 1
                page = page + 1
            )
        }.map { topHeadlinesResponse ->
            topHeadlinesResponse.articles.map { article ->
                News(
                    title = article.title,
                    description = article.description?:"",
                    urlToImage = article.urlToImage?:"",
                    publishedAt = article.publishedAt
                )
            }
        }
    }

    private fun getSourcesForCategory(
        category: String = "",
        language: String = "",
        country: String = ""
    ): Single<SourcesResponse> {
        return newsApiRestService.getSources(category, language, country)
    }

    private fun retrieveSourceIdsFromSourcesResponses(
        responses: Array<Any>
    ): List<String> {
        return responses.mapNotNull { response ->
            val sourcesResponse = response as? SourcesResponse
            if (sourcesResponse == null) {
                Timber.w("Unexpected response $response")
            }
            sourcesResponse
        }.flatMap { sourcesResponse ->
            sourcesResponse.sources.map { it.id }
        }
    }

    companion object {
        const val CATEGORY_SPORTS = "sports"
        const val CATEGORY_BUSINESS = "business"
        const val COUNTRY_US = "us"
    }
}