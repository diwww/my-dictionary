package org.maxsur.mydictionary.di.component

import dagger.Component
import org.maxsur.mydictionary.MainActivity
import org.maxsur.mydictionary.di.module.MainModule
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(): ApplicationComponent
    }
}