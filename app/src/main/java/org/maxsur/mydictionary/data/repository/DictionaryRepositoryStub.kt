package org.maxsur.mydictionary.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.domain.repository.DictionaryRepository
import java.util.*

class DictionaryRepositoryStub : DictionaryRepository {

    private val random = Random()

    private val words = listOf(
        Word("dog", "собака", Translation("EN", "RU")),
        Word("house", "дом", Translation("EN", "RU")),
        Word("glass", "стакан", Translation("EN", "RU")),
        Word("цветок", "flower", Translation("RU", "EN")),
        Word("горшок", "pot", Translation("RU", "EN")),
        Word("color", "цвет", Translation("EN", "RU")),
    ).toMutableList()

    override fun getWords(search: String?): Single<List<Word>> {
        return if (search.isNullOrBlank()) {
            Single.just(words)
        } else {
            Single.just(
                words.filter { word ->
                    word.original.contains(search, ignoreCase = true) ||
                            word.translated.contains(search, ignoreCase = true)
                }
            )
        }
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

    override fun updateWord(word: Word): Single<Word> {
        // do nothing
        return Single.just(word)
    }

    override fun getFavoriteWords(): Single<List<Word>> {
        return Single.just(words)
    }
}