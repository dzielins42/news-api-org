package pl.dzielins42.newsapi

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer

class ProgressItem : AbstractFlexibleItem<ProgressItem.ViewHolder>() {

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) {
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun getLayoutRes() = R.layout.item_progress

    override fun equals(other: Any?) = this === other

    override fun hashCode() = javaClass.hashCode()

    class ViewHolder(
        view: View, adapter: FlexibleAdapter<*>
    ) : FlexibleViewHolder(view, adapter), LayoutContainer {
        override val containerView: View?
            get() = itemView
    }
}