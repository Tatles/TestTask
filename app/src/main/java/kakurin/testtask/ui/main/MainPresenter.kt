package kakurin.testtask.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kakurin.testtask.data.RetrofitImpl
import kakurin.testtask.data.model.New
import kakurin.testtask.data.model.News
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MainPresenter() : MvpPresenter<MainView>() {
    private var isRequest = false
    private var page = 1
    private var apiService = RetrofitImpl.create()
    var isNewsExists = false
    fun onRefresh() {
        page = 1
        apiService.getNews(page = page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                viewState.setData(it)
                viewState.showOnSuccess()
                isRequest = false
                isNewsExists = true
            }, {
                viewState.showOnError(it)
            })
    }

    fun getMoreNews() {
        if (!isRequest) {
            isRequest = true
            page++
            apiService.getNews(page = page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    viewState.addData(it)
                    viewState.showOnSuccess()
                    isRequest = false
                }, {
                    viewState.showOnError(it)
                })
        }
    }

    fun clickUrl(context: Context, model: New) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(model.url));
        startActivity(context, browserIntent, null);
    }

    fun isOnline(context: Context): Boolean {
        val connectionManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}