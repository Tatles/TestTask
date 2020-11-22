package kakurin.testtask.ui.main

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kakurin.testtask.data.NewsApi
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MainPresenter(private var newsApiService: NewsApi) : MvpPresenter<MainView>() {
    private var isLoading = false
    private var page = 1
    var isNewsExists = false
    fun onRefresh() {
        page = 1
        newsApiService.getNews(page = page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                viewState.setData(it.articles)
                viewState.showOnSuccess()
                isLoading = false
                isNewsExists = true
            }, {
                viewState.showOnError(it.message)
            })
    }

    fun getMoreNews() {
        if (!isLoading) {
            isLoading = true
            page++
            newsApiService.getNews(page = page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    viewState.addData(it.articles)
                    viewState.showOnSuccess()
                    isLoading = false
                }, {
                    viewState.showOnError(it.message)
                })
        }
    }


}