package kakurin.testtask

import android.app.Application
import kakurin.testtask.di.AppComponent
import kakurin.testtask.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder().build()
    }
}