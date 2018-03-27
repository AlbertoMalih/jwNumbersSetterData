package com.example.jwnumberssetterdata

import android.support.multidex.MultiDexApplication
import com.example.jwnumberssetterdata.dagger.AppComponent
import com.example.jwnumberssetterdata.dagger.AppModule
import com.example.jwnumberssetterdata.dagger.DaggerAppComponent

class App : MultiDexApplication() {
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