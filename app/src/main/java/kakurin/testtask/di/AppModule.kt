package kakurin.testtask.di

import dagger.Module
import dagger.Provides
import kakurin.testtask.data.NewsApi

@Module
class AppModule {
    @Provides
    fun createNewsApi() = NewsApi.create()
}