package pl.dzielins42.newsapi.data.model

import java.util.*

data class News(
    val title: String,
    val description: String,
    val urlToImage: String,
    val publishedAt: Date?
)