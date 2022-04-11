package org.maxsur.mydictionary.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word

/**
 * Интерфейс репозитория для работы со словарем.
 */
interface DictionaryRepository {

    /**
     * Подписаться на изменения списка слов с переводом.
     *
     * @return [Observable] со списком слов с переводом
     */
    fun getWordsObservable(): Observable<List<Word>>

    /**
     * Выполнить поиск слов.
     *
     * @param search поисковый запрос, по которому фильтруются слова (опционально)
     */
    fun search(search: String)

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
     * @return [Completable] с результатом операции
     */
    fun saveWord(word: Word): Completable

    /**
     * Обновить слово.
     *
     * @param word обновленное слово с тем же id
     * @return [Completable] с результатом операции
     */
    fun updateWord(word: Word): Completable

    /**
     * Получить список избранных слов.
     *
     * @return список избранных слов
     */
    fun getFavoriteWords(): Single<List<Word>>
}