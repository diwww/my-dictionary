package org.maxsur.mydictionary.presentation.presenter.dictionary

import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import org.maxsur.mydictionary.domain.interactor.DictionaryInteractor
import org.maxsur.mydictionary.domain.model.Language
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.presentation.view.dictionary.DictionaryView

class DictionaryPresenter(private val interactor: DictionaryInteractor) :
    MvpPresenter<DictionaryView>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        interactor.getAllWords()
//            .subscribeOn()
//            .observeOn()
            .subscribe(this::onSuccess, this::onError)
            .also(compositeDisposable::add)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

    fun translate(word: String) {
        if (word.isNotBlank()) {
            interactor.translateAndSave(word, Translation(Language.EN, Language.RU))
                .doOnSuccess { viewState.setSearchText("") }
                .subscribe(this::onSuccess, this::onError)
                .also(compositeDisposable::add)
        }
    }

    private fun onSuccess(words: List<Word>) {
        viewState.showWords(words)
    }

    private fun onError(throwable: Throwable) {
        // todo
    }
}