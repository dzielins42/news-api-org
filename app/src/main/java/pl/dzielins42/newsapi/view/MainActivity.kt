package pl.dzielins42.newsapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.dzielins42.newsapi.Const
import pl.dzielins42.newsapi.R
import timber.log.Timber

class MainActivity : AppCompatActivity(), FlexibleAdapter.EndlessScrollListener {

    private val viewModel by viewModel<NewsListViewModel>()

    private val flexibleAdapter = FlexibleAdapter<IFlexible<*>>(emptyList())
        .setEndlessScrollListener(this, ProgressItem())
        // Remove this when list is taken from ViewModel
        .setLoadingMoreAtStartUp(true)
        .setEndlessPageSize(Const.PAGE_SIZE)

    // FlexibleAdapter requires only newly loaded content, but ViewModel returns
    // whole loaded content, this is used to create subList for FlexibleAdapter
    private var lastPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate savedInstanceState=$savedInstanceState")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUi()
        setupViewModel()
    }

    private fun setupUi() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = flexibleAdapter
        }
    }

    private fun setupViewModel() {
        viewModel.newsList.observe(this, Observer { news ->
            flexibleAdapter.onLoadMoreComplete(
                news.subList(lastPosition, news.size)
                    .map { NewsItem(it.title) }
            )
        })
    }

    //region FlexibleAdapter.EndlessScrollListener
    override fun noMoreLoad(newItemsSize: Int) {
        Timber.d("noMoreLoad newItemsSize=$newItemsSize")
    }

    override fun onLoadMore(lastPosition: Int, currentPage: Int) {
        Timber.d("onLoadMore lastPosition=$lastPosition currentPage=$currentPage")
        this.lastPosition = lastPosition
        viewModel.loadDataPage(currentPage)
    }
    //endregion
}