package org.maxsur.mydictionary.presentation.view.favorites

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import org.maxsur.mydictionary.domain.model.Word

/**
 * Вью экрана избранных слов.
 */
interface FavoritesView : MvpView {

    /**
     * Отобразить список избранных слов.
     *
     * @param words список слов
     */
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showFavoriteWords(words: List<Word>)

    /**
     * Отобразить ошибку.
     */
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError()
}