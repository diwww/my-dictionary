package org.maxsur.mydictionary.presentation.view.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.maxsur.mydictionary.R
import org.maxsur.mydictionary.domain.model.Word

/**
 * [RecyclerView.Adapter] для списка слов с переводом.
 *
 * @property favoriteClickListener слушатель клика для избранного
 */
class WordsAdapter(private val favoriteClickListener: (Word) -> Unit) : ListAdapter<Word, WordViewHolder>(WordsItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.onBind(getItem(position), favoriteClickListener)
    }

    // FIXME: почему-то не работает корректно
//    override fun onBindViewHolder(holder: WordViewHolder, position: Int, payloads: MutableList<Any>) {
//        if (payloads.isEmpty()) {
//            super.onBindViewHolder(holder, position, payloads)
//        } else {
//            if (payloads[0] == true) {
//                holder.setFavoriteState(getItem(position).favorite)
//            }
//        }
//    }
}

private class WordsItemCallback : DiffUtil.ItemCallback<Word>() {

    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Word, newItem: Word): Any? {
        return if (oldItem.favorite != newItem.favorite) true else null
    }
}
