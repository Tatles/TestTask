package kakurin.testtask.ui.main

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainPaginationScrollListener(
    private val loadMore: () -> Unit,
    private val pageSize: Int
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager
        if (layoutManager !is LinearLayoutManager) {
            Log.e("error", "layout manager is not grid layout manager")
            return
        }

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
        val isValidFirstItem = firstVisibleItemPosition >= 0
        val totalIsMoreThanVisible = totalItemCount >= pageSize

        val shouldLoadMore = isAtLastItem && isValidFirstItem && totalIsMoreThanVisible

        if (shouldLoadMore) {
            loadMore.invoke()
        }
    }
}