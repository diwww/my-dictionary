package org.maxsur.mydictionary.presentation.view.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.maxsur.mydictionary.R
import org.maxsur.mydictionary.domain.model.Word
import java.util.*
import kotlin.collections.ArrayList

/**
 * [RecyclerView.Adapter] для списка слов с переводом.
 *
 * @property words список слов с переводом
 */
class WordsAdapter : RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {

    private val words = ArrayList<Word>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.onBind(words[position])
    }

    override fun getItemCount(): Int = words.size

    fun setWords(newWords: List<Word>) {
        words.apply {
            clear()
            addAll(newWords)
        }
        notifyDataSetChanged()
    }

    /**
     * Вью холдер слова с переводом.
     */
    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val fromTextView: TextView = itemView.findViewById(R.id.text_from)
        private val toTextView: TextView = itemView.findViewById(R.id.text_to)
        private val originalWordTextView: TextView = itemView.findViewById(R.id.text_word_original)
        private val translatedWordTextView: TextView = itemView.findViewById(R.id.text_word_translated)

        fun onBind(word: Word) {
            with(word) {
                fromTextView.text = translation.from.name
                toTextView.text = translation.to.name
                originalWordTextView.text = original
                translatedWordTextView.text = translated
            }
        }
    }
}
