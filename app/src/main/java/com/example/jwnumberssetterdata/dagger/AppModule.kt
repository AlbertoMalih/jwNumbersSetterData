package com.example.jw_numbers.dagger

import android.app.Application
import android.content.Context
import com.example.jwnumberssetterdata.App
import com.example.jwnumberssetterdata.NumbersViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: App) {

    @Provides
    @Singleton
    fun providerApplicationContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideNumbersViewModel(context: Context): NumbersViewModel = NumbersViewModel(context)


}