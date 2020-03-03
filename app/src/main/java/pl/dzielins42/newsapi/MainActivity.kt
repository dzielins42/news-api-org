package pl.dzielins42.newsapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), FlexibleAdapter.EndlessScrollListener {

    private val flexibleAdapter = FlexibleAdapter<IFlexible<*>>(emptyList())
        .setEndlessScrollListener(this, ProgressItem())
        // Remove this when list is taken from ViewModel
        .setLoadingMoreAtStartUp(true)
        .setEndlessPageSize(PAGE_SIZE)

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate savedInstanceState=$savedInstanceState")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUi()
    }

    private fun setupUi() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = flexibleAdapter
        }
    }

    private fun getMockData(startIndex: Int, count: Int): List<NewsItem> {
        return ArrayList<NewsItem>().apply {
            for (i in 0 until count) {
                add(NewsItem("news #${i + startIndex}"))
            }
        }
    }

    //region FlexibleAdapter.EndlessScrollListener
    override fun noMoreLoad(newItemsSize: Int) {
        Timber.d("noMoreLoad newItemsSize=$newItemsSize")
    }

    override fun onLoadMore(lastPosition: Int, currentPage: Int) {
        Timber.d("onLoadMore lastPosition=$lastPosition currentPage=$currentPage")
        val newData = getMockData(lastPosition, PAGE_SIZE)
        flexibleAdapter.onLoadMoreComplete(newData)
    }
    //endregion

    companion object {
        private const val PAGE_SIZE = 100
    }
}