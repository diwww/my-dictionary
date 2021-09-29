package org.maxsur.mydictionary

import android.app.Application
import org.maxsur.mydictionary.di.component.ApplicationComponent
import org.maxsur.mydictionary.di.component.DaggerApplicationComponent

class DictionaryApplication : Application() {

    lateinit var component: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.create()
    }
}