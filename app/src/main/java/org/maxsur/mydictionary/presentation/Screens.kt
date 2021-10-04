package org.maxsur.mydictionary.presentation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import org.maxsur.mydictionary.presentation.view.dictionary.DictionaryFragment
import org.maxsur.mydictionary.presentation.view.favorites.FavoritesFragment

object Screens {
    fun dictionary() = FragmentScreen { DictionaryFragment.newInstance() }
    fun favorites() = FragmentScreen { FavoritesFragment.newInstance() }
}