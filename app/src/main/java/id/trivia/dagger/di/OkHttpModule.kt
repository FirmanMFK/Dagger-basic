package id.trivia.dagger.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module // class dari object OkHttpClient agar menjadi penyedia bagi class lain

class OkHttpModule {

    @Provides //menjadi penyedia bagi class lain/fungsi
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides //menyediakan object bagi class lain
    @Singleton //objeck penyedia yang bersifat singleton
    fun provideHttpLogingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return httpLoggingInterceptor
    }
}