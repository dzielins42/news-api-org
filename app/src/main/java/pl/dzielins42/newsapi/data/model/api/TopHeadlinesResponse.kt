package pl.dzielins42.newsapi.data.model.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class TopHeadlinesResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<Item>
) {
    data class Item(
        // Only fields interesting in the context of this application
        // are mapped
        // API does not specify which fields are optional
        // I know for a fact that description may be null
        // I am not sure what about other fields
        // I assume that API is sane and title and publishedAt
        // are not null
        @SerializedName("title")
        val title: String,
        @SerializedName("description")
        val description: String?,
        @SerializedName("urlToImage")
        val urlToImage: String?,
        @SerializedName("publishedAt")
        val publishedAt: Date
    )
}