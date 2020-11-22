package kakurin.testtask.di

import dagger.Component
import kakurin.testtask.ui.main.MainActivity


@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}