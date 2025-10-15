package com.example.unit5_pathway2_amphibians

import android.app.Application
import com.example.unit5_pathway2_amphibians.data.AppContainer
import com.example.unit5_pathway2_amphibians.data.DefaultAppContainer

class AmphibiansApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
