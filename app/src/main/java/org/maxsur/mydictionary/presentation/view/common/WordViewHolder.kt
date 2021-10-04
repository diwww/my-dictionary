package org.maxsur.mydictionary.presentation.view.common

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.maxsur.mydictionary.R
import org.maxsur.mydictionary.domain.model.Word

/**
 * Вью холдер слова с переводом.
 *
 * @param itemView коренная вьюха
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