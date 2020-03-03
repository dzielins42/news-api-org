package pl.dzielins42.newsapi.view

import android.text.format.DateFormat
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.items.IHolder
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news.*
import pl.dzielins42.newsapi.R
import pl.dzielins42.newsapi.data.model.News

class NewsItem(
    private val model: News
) : AbstractFlexibleItem<NewsItem.NewsItemViewHolder>(), IHolder<News> {

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: NewsItemViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        val dateFormat = DateFormat.getDateFormat(holder.itemView.context)
        val titleWithDate =
            (model.publishedAt?.let { dateFormat.format(it) + ": " } ?: "") + model.title
        holder.titleWithDate.text = titleWithDate
        holder.description.text = model.description
        Glide.with(holder.thumbnail)
            .load(model.urlToImage)
            .error(R.drawable.ic_error)
            .into(holder.thumbnail)
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): NewsItemViewHolder {
        return NewsItemViewHolder(view, adapter)
    }

    override fun getLayoutRes() = R.layout.item_news

    override fun equals(other: Any?) = model == (other as? NewsItem)?.model

    override fun hashCode() = model.hashCode()

    override fun getModel(): News = model

    class NewsItemViewHolder(
        view: View, adapter: FlexibleAdapter<*>
    ) : FlexibleViewHolder(view, adapter), LayoutContainer {
        override val containerView: View?
            get() = itemView
    }
}