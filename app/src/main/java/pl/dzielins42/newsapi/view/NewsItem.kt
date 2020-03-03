package pl.dzielins42.newsapi.view

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news.*
import pl.dzielins42.newsapi.R

class NewsItem(
    private val label: String
) : AbstractFlexibleItem<NewsItem.NewsItemViewHolder>() {

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: NewsItemViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        holder.label.text = label
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): NewsItemViewHolder {
        return NewsItemViewHolder(view, adapter)
    }

    override fun getLayoutRes() = R.layout.item_news

    override fun equals(other: Any?) = label == (other as? NewsItem)?.label


    override fun hashCode() = label.hashCode()

    class NewsItemViewHolder(
        view: View, adapter: FlexibleAdapter<*>
    ) : FlexibleViewHolder(view, adapter), LayoutContainer {
        override val containerView: View?
            get() = itemView
    }
}