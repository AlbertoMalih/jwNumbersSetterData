package com.example.jw_numbers.dagger

import com.example.jwnumberssetterdata.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class))

interface AppComponent {
    fun inject(activity: MainActivity)
}