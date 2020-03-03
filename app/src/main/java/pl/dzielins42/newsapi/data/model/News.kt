package pl.dzielins42.newsapi.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class News(
    val title: String,
    val description: String,
    val urlToImage: String,
    val publishedAt: Date?,
    val content: String
) : Parcelable