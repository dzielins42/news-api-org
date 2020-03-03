package pl.dzielins42.newsapi.data.model.api

import com.google.gson.annotations.SerializedName

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
        @SerializedName("title")
        val title: String
    )
}