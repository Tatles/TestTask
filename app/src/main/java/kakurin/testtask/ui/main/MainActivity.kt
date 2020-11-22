package kakurin.testtask.ui.main

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kakurin.testtask.R
import kakurin.testtask.data.model.New
import kakurin.testtask.data.model.News
import kakurin.testtask.ui.adapters.Adapter
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter


class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var adapter: Adapter
    private val mainPresenter by moxyPresenter { MainPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "News"
        rvSetUp()
        if (!mainPresenter.isNewsExists) {
            mainPresenter.onRefresh()
        }
        button_no_internet.setOnClickListener {
            if (mainPresenter.isOnline(this)) {
                mainPresenter.onRefresh()
            }
        }
    }

    private fun rvSetUp() {
        adapter = Adapter(::clickUrlContext)
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapter
        rv_main.addOnScrollListener(
            MainPaginationScrollListener(
                { mainPresenter.getMoreNews() },
                10
            )
        )
    }

    private fun clickUrlContext(model: New) {
        mainPresenter.clickUrl(this, model)
    }

    override fun setData(data: News) {
        adapter.setData(data.articles)
    }

    override fun addData(data: News) {
        adapter.addData(data.articles)
    }

    override fun showOnSuccess() {
        rv_main.visibility = RecyclerView.VISIBLE
        no_internet.visibility = LinearLayout.GONE
    }

    override fun showOnError(error: Throwable) {
        if (!mainPresenter.isOnline(this)) {
            showNoInternet()
        } else {
            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun showNoInternet() {
        rv_main.visibility = RecyclerView.INVISIBLE
        no_internet.visibility = TextView.VISIBLE
    }
}
