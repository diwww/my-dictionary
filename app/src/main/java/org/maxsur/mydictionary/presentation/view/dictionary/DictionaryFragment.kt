package org.maxsur.mydictionary.presentation.view.dictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.maxsur.mydictionary.R

/**
 * Экран словаря.
 */
class DictionaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dictionary, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = DictionaryFragment()
    }
}