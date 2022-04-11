package org.maxsur.mydictionary.presentation.presenter.dictionary

import android.util.Log
import com.github.terrakok.cicerone.Router
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import moxy.InjectViewState
import moxy.MvpPresenter
import org.maxsur.mydictionary.domain.interactor.DictionaryInteractor
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.presentation.Screens
import org.maxsur.mydictionary.presentation.view.dictionary.DictionaryView
import org.maxsur.mydictionary.util.RxSchedulers
import java.util.concurrent.TimeUnit

private const val TAG = "DictionaryPresenter"
private const val DEBOUNCE_MS = 500L

/**
 * Презентер экрана словаря.
 *
 * @property interactor интерактор словаря
 * @property rxSchedulers шедулеры rx потоков
 * @property router роутер приложения
 */
@InjectViewState
class DictionaryPresenter(
    private val interactor: DictionaryInteractor,
    private val rxSchedulers: RxSchedulers,
    private val router: Router
) : MvpPresenter<DictionaryView>() {

    private val compositeDisposable = CompositeDisposable()
    private val searchPublishSubject = PublishSubject.create<String>()

    override fun onFirstViewAttach() {
        searchPublishSubject.toFlowable(BackpressureStrategy.LATEST)
            .debounce(DEBOUNCE_MS, TimeUnit.MILLISECONDS, rxSchedulers.computation)
            .subscribe { interactor.searchWords(it) }
            .also(compositeDisposable::add)

        interactor.getWordsObservable()
            .observeOn(rxSchedulers.main)
            .subscribe(this::onSuccess, this::onError)
            .also(compositeDisposable::add)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    /**
     * Выполнить поиск слов.
     *
     * @param search поисковый запрос
     */
    fun searchWords(search: String) {
        searchPublishSubject.onNext(search)
    }

    /**
     * Перевести новое слово.
     *
     * @param word слово для перевода
     * @param from язык, с которого осуществляется перевод
     * @param to язык, на который осуществляется перевод
     */
    fun translate(word: String, from: String, to: String) {
        if (word.isNotBlank()) {
            interactor.translateAndSave(word, Translation(from, to))
                .subscribeOn(rxSchedulers.io)
                .observeOn(rxSchedulers.main)
                .doOnSubscribe { viewState.showProgress(true) }
                .doFinally { viewState.showProgress(false) }
                .doOnComplete { viewState.setSearchText("") }
                .subscribe({}, { onError(it) })
                .also(compositeDisposable::add)
        }
    }

    /**
     * Поменять языки местами.
     *
     * @param fromPos язык, с которого осуществляется перевод
     * @param toPos язык, на который осуществляется перевод
     */
    fun reverseLanguages(fromPos: Int, toPos: Int) {
        viewState.setSpinnersSelection(toPos, fromPos)
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

    /**
     * Открыть экран с избранными словами.
     */
    fun openFavorites() {
        router.navigateTo(Screens.favorites())
    }

    private fun onSuccess(words: List<Word>) {
        Log.d(TAG, "onSuccess: $words")
        viewState.showWords(words)
    }

    private fun onError(throwable: Throwable) {
        logError(throwable)
        viewState.showError()
    }

    private fun logError(throwable: Throwable) {
        Log.e(TAG, throwable.message, throwable)
    }
}
