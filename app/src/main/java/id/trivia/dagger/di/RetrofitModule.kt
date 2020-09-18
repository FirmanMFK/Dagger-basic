package id.trivia.dagger.di

import dagger.Module
import dagger.Provides
import id.trivia.dagger.api.Endpoints
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule { //menyediakan object endpoint yang terdaftar pada class Retrofit

    @Provides
    @Singleton
    fun provideEndpoints(okHttpClient: OkHttpClient): Endpoints {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(Endpoints::class.java)
    }
}