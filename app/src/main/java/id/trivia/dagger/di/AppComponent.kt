package id.trivia.dagger.di

import dagger.Component
import id.trivia.dagger.MainActivity
import id.trivia.dagger.api.Endpoints
import javax.inject.Singleton

@Component(modules = [(OkHttpModule::class), (RetrofitModule::class)])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity) //object dengan annotation tidak perlu dideklarasikan lagi
}