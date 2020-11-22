package kakurin.testtask.ui.main

import kakurin.testtask.data.model.Article
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface MainView : MvpView {
    fun setData(data: List<Article>)
    fun addData(data: List<Article>)
    fun showOnSuccess()
    fun showOnError(error: Throwable)
}