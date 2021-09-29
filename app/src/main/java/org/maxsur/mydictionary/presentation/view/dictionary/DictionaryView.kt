package org.maxsur.mydictionary.presentation.view.dictionary

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import org.maxsur.mydictionary.domain.model.Word

interface DictionaryView : MvpView {

    /**
     * Отобразить список слов.
     *
     * @param words список слов
     */
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showWords(words: List<Word>)

    /**
     * Отобразить прогресс бар.
     *
     * @param show флаг отображения
     */
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress(show: Boolean)

    /**
     * Задать текст поиска.
     *
     * @param search текст поиска
     */
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSearchText(search: String)
}