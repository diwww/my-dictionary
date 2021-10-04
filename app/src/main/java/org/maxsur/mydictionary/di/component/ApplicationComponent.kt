package org.maxsur.mydictionary.di.component

import dagger.Component
import org.maxsur.mydictionary.di.module.DatabaseModule
import org.maxsur.mydictionary.presentation.view.MainActivity
import org.maxsur.mydictionary.di.module.MainModule
import org.maxsur.mydictionary.di.module.NavigationModule
import org.maxsur.mydictionary.presentation.view.dictionary.DictionaryFragment
import org.maxsur.mydictionary.presentation.view.favorites.FavoritesFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class, NavigationModule::class, DatabaseModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: DictionaryFragment)
    fun inject(fragment: FavoritesFragment)

    @Component.Factory
    interface Factory {
        fun create(
            navigationModule: NavigationModule,
            databaseModule: DatabaseModule
        ): ApplicationComponent
    }
}