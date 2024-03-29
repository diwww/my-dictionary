package org.maxsur.mydictionary.presentation.view.dictionary

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
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
import org.maxsur.mydictionary.presentation.view.common.WordsAdapter
import javax.inject.Inject
import javax.inject.Provider

/**
 * Экран словаря.
 */
class DictionaryFragment : MvpAppCompatFragment(R.layout.fragment_dictionary), DictionaryView {

    @Inject
    lateinit var presenterProvider: Provider<DictionaryPresenter>

    private lateinit var searchEditText: EditText
    private lateinit var translateButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var reverseButton: ImageButton

    private lateinit var textChangedListener: AfterTextChanged
    private val wordsAdapter = WordsAdapter { word, position ->
        presenter.switchFavorite(word, position)
    }

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as DictionaryApplication).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setupViews()
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        textChangedListener = AfterTextChanged(presenter::searchWords)
            .also(searchEditText::addTextChangedListener)
        presenter.getWords(searchEditText.text.toString())
    }

    override fun onStop() {
        super.onStop()
        searchEditText.removeTextChangedListener(textChangedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.favorites) {
            presenter.openFavorites()
            true
        } else {
            false
        }
    }

    override fun showWords(words: List<Word>) {
        wordsAdapter.setWords(words)
    }

    override fun showProgress(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        translateButton.visibility = if (show) View.GONE else View.VISIBLE
        searchEditText.isEnabled = !show
    }

    override fun showError() {
        Toast.makeText(requireContext(), R.string.error_general, Toast.LENGTH_SHORT).show()
    }

    override fun setSearchText(search: String) {
        searchEditText.setText(search)
    }

    override fun setSpinnersSelection(fromPos: Int, toPos: Int) {
        spinnerFrom.setSelection(fromPos)
        spinnerTo.setSelection(toPos)
    }

    override fun updateWord(word: Word, position: Int) {
        wordsAdapter.updateWord(word, position)
    }

    private fun initViews(view: View) = with(view) {
        searchEditText = findViewById(R.id.edit_text_search)
        translateButton = findViewById(R.id.button_translate)
        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_bar)
        spinnerFrom = findViewById(R.id.spinner_lang_from)
        spinnerTo = findViewById(R.id.spinner_lang_to)
        reverseButton = findViewById(R.id.button_reverse)
    }

    private fun setupViews() {
        translateButton.setOnClickListener {
            presenter.translate(
                searchEditText.text.toString(),
                spinnerFrom.selectedItem.toString(),
                spinnerTo.selectedItem.toString()
            )
        }
        recyclerView.apply {
            adapter = wordsAdapter
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
        setupSpinner(spinnerFrom)
        setupSpinner(spinnerTo)
        reverseButton.setOnClickListener {
            presenter.reverseLanguages(
                spinnerFrom.selectedItemPosition,
                spinnerTo.selectedItemPosition
            )
        }
    }

    private fun setupSpinner(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.languages,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = DictionaryFragment()
    }
}

private class AfterTextChanged(private val block: (String) -> Unit) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // do nothing
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // do nothing
    }

    override fun afterTextChanged(s: Editable?) {
        block(s.toString())
    }
}