package org.maxsur.mydictionary

import android.app.Application
import org.maxsur.mydictionary.di.component.ApplicationComponent
import org.maxsur.mydictionary.di.component.DaggerApplicationComponent
import org.maxsur.mydictionary.di.module.NavigationModule

class DictionaryApplication : Application() {

    lateinit var component: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.factory()
            .create(navigationModule = NavigationModule())
    }
}