package org.maxsur.mydictionary.presentation.presenter.favorites

import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import org.maxsur.mydictionary.domain.interactor.DictionaryInteractor
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.presentation.view.favorites.FavoritesView
import org.maxsur.mydictionary.util.RxSchedulers

private const val TAG = "FavoritesPresenter"

/**
 * Презентер экрана избранных слов.
 *
 * @property interactor интерактор для работы со словарем
 * @property rxSchedulers планировщики rx потоков
 */
@InjectViewState
class FavoritesPresenter(
    private val interactor: DictionaryInteractor,
    private val rxSchedulers: RxSchedulers
) : MvpPresenter<FavoritesView>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        interactor.getFavoriteWords()
            .subscribeOn(rxSchedulers.io)
            .observeOn(rxSchedulers.main)
            .subscribe(this::onSuccess, this::onError)
            .also(compositeDisposable::add)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    /**
     * Изменить состояние избранного у слова.
     *
     * @param word слово
     */
    fun switchFavorite(word: Word) {
        interactor.switchFavorite(word)
            .subscribeOn(rxSchedulers.io)
            .observeOn(rxSchedulers.main)
            .subscribe({ }, this::onError)
            .also(compositeDisposable::add)
    }

    private fun onSuccess(words: List<Word>) {
        viewState.showFavoriteWords(words)
    }

    private fun onError(throwable: Throwable) {
        Log.d(TAG, throwable.message, throwable)
        viewState.showError()
    }
}