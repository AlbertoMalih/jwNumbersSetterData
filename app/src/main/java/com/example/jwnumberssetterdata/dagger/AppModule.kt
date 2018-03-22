package com.example.jwnumberssetterdata.dagger

import android.app.Application
import android.content.Context
import com.example.jwnumberssetterdata.App
import com.example.jwnumberssetterdata.NumbersRepository
import com.example.jwnumberssetterdata.NumbersViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: App) {

    @Provides
    @Singleton
    fun providerApplicationContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideNumbersViewModel(context: Context, repository: NumbersRepository): NumbersViewModel = NumbersViewModel(context, repository)

    @Provides
    @Singleton
    fun provideNumbersRepository(): NumbersRepository = NumbersRepository()


}