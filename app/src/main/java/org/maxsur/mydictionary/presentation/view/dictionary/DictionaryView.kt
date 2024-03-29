package org.maxsur.mydictionary.presentation.view.dictionary

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
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
     * Отобразить ошибку.
     */
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError()

    /**
     * Задать текст поиска.
     *
     * @param search текст поиска
     */
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setSearchText(search: String)

    /**
     * Задать выбранные элементы в спинерах языка.
     *
     * @param fromPos новая позиция в спинере языка, с которого переводить
     * @param toPos новая позиция в спинере языка, на который переводить
     */
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSpinnersSelection(fromPos: Int, toPos: Int)

    /**
     * Обновить слово на определенной позиции.
     *
     * @param word
     *
     */
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun updateWord(word: Word, position: Int)
}