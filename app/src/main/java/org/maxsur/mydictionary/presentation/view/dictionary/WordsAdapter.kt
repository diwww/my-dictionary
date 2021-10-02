package org.maxsur.mydictionary.presentation.view.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.maxsur.mydictionary.R
import org.maxsur.mydictionary.domain.model.Word

/**
 * [RecyclerView.Adapter] для списка слов с переводом.
 *
 * @property favoriteClickListener слушатель клика для избранного
 */
class WordsAdapter(private val favoriteClickListener: (Word, Int) -> Unit) :
    RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {

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

    fun setWords(newWords: List<Word>) {
        words.apply {
            clear()
            addAll(newWords)
        }
        notifyDataSetChanged()
    }

    fun updateWord(word: Word, position: Int) {
        words[position] = word
        notifyItemChanged(position)
    }

    /**
     * Вью холдер слова с переводом.
     */
    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val fromTextView: TextView = itemView.findViewById(R.id.text_from)
        private val toTextView: TextView = itemView.findViewById(R.id.text_to)
        private val originalWordTextView: TextView = itemView.findViewById(R.id.text_word_original)
        private val translatedWordTextView: TextView =
            itemView.findViewById(R.id.text_word_translated)
        private val favoriteButton: ImageButton = itemView.findViewById(R.id.button_favorite)

        fun onBind(word: Word, favoriteClickListener: (Word, Int) -> Unit) {
            with(word) {
                fromTextView.text = translation.from
                toTextView.text = translation.to
                originalWordTextView.text = original
                translatedWordTextView.text = translated
                val icon = if (favorite) R.drawable.ic_star_fill_24 else R.drawable.ic_star_24
                favoriteButton.setImageResource(icon)
                favoriteButton.setOnClickListener {
                    favoriteClickListener.invoke(word, adapterPosition)
                }
            }
        }
    }
}
