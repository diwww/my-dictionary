package org.maxsur.mydictionary.presentation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import org.maxsur.mydictionary.presentation.view.dictionary.DictionaryFragment

object Screens {
    fun dictionary() = FragmentScreen { DictionaryFragment.newInstance() }
}