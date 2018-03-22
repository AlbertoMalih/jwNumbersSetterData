package com.example.jwnumberssetterdata

import android.app.Application
import com.example.jw_numbers.dagger.DaggerAppComponent
import com.example.jwnumberssetterdata.dagger.AppComponent
import com.example.jwnumberssetterdata.dagger.AppModule

class App : Application() {
    companion object {
        lateinit var INSTANCE: App
        lateinit var component: AppComponent
    }


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun appComponent(): AppComponent = component
}