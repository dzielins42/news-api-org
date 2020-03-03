package pl.dzielins42.newsapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val flexibleAdapter = FlexibleAdapter<NewsItem>(emptyList())

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

        flexibleAdapter.updateDataSet(getMockData(1, 200), true)
    }

    private fun getMockData(from: Int, to: Int): List<NewsItem> {
        return ArrayList<NewsItem>().apply {
            for (i in from..to) {
                add(NewsItem("news #$i"))
            }
        }
    }
}
