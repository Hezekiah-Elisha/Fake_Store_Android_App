package com.olefaent.fakestore

import android.app.Application
import com.olefaent.fakestore.data.AppContainer
import com.olefaent.fakestore.data.DefaultAppContainer

class FakeStoreApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = DefaultAppContainer()
    }
}