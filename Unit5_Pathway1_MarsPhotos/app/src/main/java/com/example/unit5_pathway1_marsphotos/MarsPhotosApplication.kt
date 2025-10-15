package com.example.unit5_pathway1_marsphotos

import android.app.Application
import com.example.unit5_pathway1_marsphotos.data.AppContainer
import com.example.unit5_pathway1_marsphotos.data.DefaultAppContainer


class MarsPhotosApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
