package pl.dzielins42.newsapi.data

import io.reactivex.Single
import pl.dzielins42.newsapi.data.model.api.SourcesResponse
import pl.dzielins42.newsapi.data.model.api.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiRestService {
    @GET("sources")
    fun getSources(
        @Query("category") category: String = "",
        @Query("language") language: String = "",
        @Query("country") country: String = ""
    ): Single<SourcesResponse>

    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String = "",
        @Query("category") category: String = "",
        @Query("sources") sources: List<String> = emptyList(),
        @Query("q") query: String = "",
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int
    ): Single<TopHeadlinesResponse>
}