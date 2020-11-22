package kakurin.testtask.data
import io.reactivex.Single
import kakurin.testtask.data.models.News
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {
    @GET("everything")
    fun getNews(
        @Query("apiKey") apiKey: String = "e7a4d3493ec84a1a9232789bf7a943cf",
        @Query("q") popular: String = "sports",
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int
    ): Single<News>
    companion object Factory {
        fun create(): NewsApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsapi.org/v2/")
                .build()

            return retrofit.create(NewsApi::class.java)
        }
    }
}
