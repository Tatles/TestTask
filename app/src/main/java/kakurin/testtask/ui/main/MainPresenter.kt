package kakurin.testtask.ui.main

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kakurin.testtask.data.NewsApi
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MainPresenter() : MvpPresenter<MainView>() {
    private var isLoading = false
    private var page = 1
    private var apiService = NewsApi.create() //todo Dagger2
    var isNewsExists = false
    fun onRefresh() {
        page = 1
        apiService.getNews(page = page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                viewState.setData(it.articles)
                viewState.showOnSuccess()
                isLoading = false
                isNewsExists = true
            }, {
                viewState.showOnError(it)
            })
    }

    fun getMoreNews() {
        if (!isLoading) {
            isLoading = true
            page++
            apiService.getNews(page = page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    viewState.addData(it.articles)
                    viewState.showOnSuccess()
                    isLoading = false
                }, {
                    viewState.showOnError(it)
                })
        }
    }


}