package org.maxsur.mydictionary.presentation.view.favorites

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.maxsur.mydictionary.DictionaryApplication
import org.maxsur.mydictionary.R
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.presentation.presenter.favorites.FavoritesPresenter
import org.maxsur.mydictionary.presentation.view.common.WordsAdapter
import javax.inject.Inject
import javax.inject.Provider

class FavoritesFragment : MvpAppCompatFragment(R.layout.fragment_favorites), FavoritesView {

    @Inject
    lateinit var presenterProvider: Provider<FavoritesPresenter>

    private lateinit var recyclerView: RecyclerView
    private val wordsAdapter = WordsAdapter { word  ->
        presenter.switchFavorite(word)
    }

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as DictionaryApplication).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.apply {
            adapter = wordsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

    override fun showFavoriteWords(words: List<Word>) {
        wordsAdapter.submitList(words)
    }

    override fun showError() {
        Toast.makeText(requireContext(), R.string.error_general, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}