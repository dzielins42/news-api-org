package pl.dzielins42.newsapi.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_content.*
import pl.dzielins42.newsapi.R
import pl.dzielins42.newsapi.data.model.News

class ContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val news = intent.getParcelableExtra<News>(EXTRA_NEWS)
        if (news?.content.isNullOrBlank()) {
            Toast.makeText(
                this,
                R.string.error_no_content,
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
        content.text = news.content
    }

    companion object {
        private const val EXTRA_NEWS = "pl.dzielins42.newsapi.view.ContentActivity.NEWS"

        fun getIntent(context: Context, news: News): Intent {
            return Intent(context, ContentActivity::class.java).apply {
                putExtra(EXTRA_NEWS, news)
            }
        }
    }
}
