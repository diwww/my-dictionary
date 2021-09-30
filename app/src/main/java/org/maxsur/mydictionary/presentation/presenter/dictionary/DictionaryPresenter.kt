package org.maxsur.mydictionary.presentation.presenter.dictionary

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import org.maxsur.mydictionary.domain.interactor.DictionaryInteractor
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.presentation.view.dictionary.DictionaryView

class DictionaryPresenter(private val interactor: DictionaryInteractor) :
    MvpPresenter<DictionaryView>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        interactor.getAllWords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onSuccess, this::onError)
            .also(compositeDisposable::add)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

    fun translate(word: String, from: String, to: String) {
        if (word.isNotBlank()) {
            interactor.translateAndSave(word, Translation(from, to))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress(true) }
                .doOnEvent { _, _ -> viewState.showProgress(false) }
                .doOnSuccess { viewState.setSearchText("") }
                .subscribe(this::onSuccess, this::onError)
                .also(compositeDisposable::add)
        }
    }

    fun reverseLanguages(fromPos: Int, toPos: Int) {
        viewState.setSpinnersSelection(toPos, fromPos)
    }

    private fun onSuccess(words: List<Word>) {
        viewState.showWords(words)
    }

    private fun onError(throwable: Throwable) {
        // todo
    }
}