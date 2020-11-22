package kakurin.testtask.ui.main

import kakurin.testtask.data.model.News
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface MainView:MvpView {
    fun setData(data: News)
    fun addData(data: News)
    fun showOnSuccess()
    fun showOnError(error: Throwable)
}