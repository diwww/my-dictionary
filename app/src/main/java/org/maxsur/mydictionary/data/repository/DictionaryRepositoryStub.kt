package org.maxsur.mydictionary.data.repository

import io.reactivex.Single
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.domain.repository.DictionaryRepository

class DictionaryRepositoryStub : DictionaryRepository {

    private val words = listOf(
        Word("dog", "собака", Translation("EN", "RU")),
        Word("house", "дом", Translation("EN", "RU"))
    ).toMutableList()

    override fun getWords(search: String?): Single<List<Word>> {
        return Single.just(words)
    }

    override fun translate(word: String, translation: Translation): Single<Word> {
        return Single.just(Word(word, "translated", translation))
    }

    override fun saveWord(word: Word): Single<Word> {
        words.add(0, word)
        return Single.just(word)
    }
}