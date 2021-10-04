package org.maxsur.mydictionary.presentation.view.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.maxsur.mydictionary.R
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.presentation.view.dictionary.WordViewHolder

/**
 * [RecyclerView.Adapter] для списка избранных слов.
 *
 * @property favoriteClickListener слушатель клика для избранного
 */
class FavoritesAdapter(private val favoriteClickListener: (Word, Int) -> Unit) :
    RecyclerView.Adapter<WordViewHolder>() {

    private val words = ArrayList<Word>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.onBind(words[position], favoriteClickListener)
    }

    override fun getItemCount(): Int = words.size


}