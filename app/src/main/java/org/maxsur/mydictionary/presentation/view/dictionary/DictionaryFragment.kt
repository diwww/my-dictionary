package org.maxsur.mydictionary.presentation.view.dictionary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.maxsur.mydictionary.DictionaryApplication
import org.maxsur.mydictionary.R
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.presentation.presenter.dictionary.DictionaryPresenter
import javax.inject.Inject
import javax.inject.Provider

/**
 * Экран словаря.
 */
class DictionaryFragment : MvpAppCompatFragment(), DictionaryView {

    @Inject
    lateinit var presenterProvider: Provider<DictionaryPresenter>

    private lateinit var searchEditText: EditText
    private lateinit var translateButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val wordsAdapter = WordsAdapter()

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as DictionaryApplication).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dictionary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setupViews()
    }

    override fun showWords(words: List<Word>) {
        wordsAdapter.setWords(words)
    }

    override fun showProgress(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun setSearchText(search: String) {
        searchEditText.setText(search)
    }

    private fun initViews(view: View) {
        searchEditText = view.findViewById(R.id.edit_text_search)
        translateButton = view.findViewById(R.id.button_translate)
        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
    }

    private fun setupViews() {
        translateButton.setOnClickListener {
            presenter.translate(searchEditText.text.toString())
        }
        recyclerView.apply {
            adapter = wordsAdapter
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = DictionaryFragment()
    }
}