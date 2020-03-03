package pl.dzielins42.newsapi.data.model.api

import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("sources")
    val sources: List<Item>
) {
    data class Item(
        // Only id is interesting in the context of this application
        @SerializedName("id")
        val id: String
    )
}