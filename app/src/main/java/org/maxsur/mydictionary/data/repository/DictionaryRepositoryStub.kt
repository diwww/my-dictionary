package org.maxsur.mydictionary.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.domain.repository.DictionaryRepository
import java.util.*

class DictionaryRepositoryStub : DictionaryRepository {

    private val random = Random()
    private val searchSubject = PublishSubject.create<String>()

    private val words = listOf(
        Word("dog", "собака", Translation("EN", "RU")),
        Word("house", "дом", Translation("EN", "RU")),
        Word("glass", "стакан", Translation("EN", "RU")),
        Word("цветок", "flower", Translation("RU", "EN")),
        Word("горшок", "pot", Translation("RU", "EN")),
        Word("color", "цвет", Translation("EN", "RU")),
    ).toMutableList()

    override fun getWordsObservable(): Observable<List<Word>> {
        return searchSubject.startWith("")
            .switchMap { search ->
                if (search.isBlank()) {
                    Observable.just(words)
                } else {
                    Observable.just(words.filter { word ->
                        word.original.contains(other = search, ignoreCase = true) ||
                                word.translated.contains(other = search, ignoreCase = true)
                    })
                }
            }
    }

    override fun search(search: String) {
        searchSubject.onNext(search)
    }

    override fun translate(word: String, translation: Translation): Single<Word> {
        return Single.fromCallable {
            Thread.sleep(2000)
            if (random.nextBoolean()) {
                throw RuntimeException()
            }
            Word(word, "translated", translation)
        }
    }

    override fun saveWord(word: Word): Completable {
        return Completable.fromAction { words.add(word) }
    }

    override fun updateWord(word: Word): Completable {
        // do nothing
        return Completable.complete()
    }

    override fun getFavoriteWords(): Single<List<Word>> {
        return Single.just(words)
    }
}