package kakurin.testtask.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kakurin.testtask.R
import kakurin.testtask.data.model.Article
import kakurin.testtask.ui.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter


class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var newsAdapter: NewsAdapter
    private val mainPresenter by moxyPresenter { MainPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = getString(R.string.news)
        rvSetUp()
        if (!mainPresenter.isNewsExists) {
            mainPresenter.onRefresh()
        }
        button_no_internet.setOnClickListener {
            if (isOnline()) {
                mainPresenter.onRefresh()
            }
        }
    }

    private fun rvSetUp() {
        newsAdapter = NewsAdapter(::clickUrl)
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = newsAdapter
        rv_main.addOnScrollListener(
            MainPaginationScrollListener(
                { mainPresenter.getMoreNews() },
                10
            )
        )
    }

    override fun setData(data: List<Article>) {
        newsAdapter.setData(data)
    }

    override fun addData(data: List<Article>) {
        newsAdapter.addData(data)
    }

    override fun showOnSuccess() {
        rv_main.visibility = RecyclerView.VISIBLE
        no_internet.visibility = LinearLayout.INVISIBLE
    }

    override fun showOnError(error: Throwable) {
        if (!isOnline()) {
            showNoInternet()
        } else {
            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun showNoInternet() {
        rv_main.visibility = RecyclerView.INVISIBLE
        no_internet.visibility = TextView.VISIBLE
    }
    private fun clickUrl(model: Article) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(model.url));
        ContextCompat.startActivity(this, browserIntent, null);
    }

    private fun isOnline(): Boolean {
        val connectionManager=
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}
