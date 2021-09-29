package org.maxsur.mydictionary.domain.repository

import io.reactivex.Single
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word

/**
 * Интерфейс репозитория для работы со словарем.
 */
interface DictionaryRepository {

    /**
     * Получить список слов с переводом.
     *
     * @param search поисковый запрос, по которому фильтруются слова (опционально)
     * @return список слов с переводом
     */
    fun getWords(search: String? = null): Single<List<Word>>

    /**
     * Перевести слово.
     *
     * @param word слово для перевода
     * @param translation направление перевода
     * @return переведенное слово
     */
    fun translate(word: String, translation: Translation): Single<Word>

    /**
     * Сохранить переведенное слово в БД.
     *
     * @param word переведенное слово
     * @return сохраненное слово
     */
    fun saveWord(word: Word): Single<Word>
}