package pl.dzielins42.newsapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        flexibleAdapter.addListener(FlexibleAdapter.OnItemClickListener { view, position ->
            (flexibleAdapter.getItem(position) as? NewsItem)?.let {
                Timber.d("Item ${it.model} clicked")
                startActivity(ContentActivity.getIntent(this, it.model))
            }
            true
        })
    }

    private fun setupViewModel() {
        viewModel.newsList.observe(this, Observer { news ->
            flexibleAdapter.onLoadMoreComplete(
                news.subList(lastPosition, news.size)
                    .map { NewsItem(it) }
            )
        })

        viewModel.error.observe(this, Observer { error ->
            if (error != null) {
                flexibleAdapter.onLoadMoreComplete(null)
                Toast.makeText(
                    this,
                    error.message,
                    Toast.LENGTH_LONG
                ).show()
                viewModel.dismissError()
            }
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