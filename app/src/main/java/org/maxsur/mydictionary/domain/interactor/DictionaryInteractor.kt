package org.maxsur.mydictionary.domain.interactor

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.domain.repository.DictionaryRepository

private const val TAG = "DictionaryInteractor"

/**
 * Интерактор для работы со словарем.
 *
 * @property repository репозиторий для работы со словарем
 */
class DictionaryInteractor(private val repository: DictionaryRepository) {

    /**
     * Подписаться на изменения списка слов с переводом.
     *
     * @return [Observable] со списком слов с переводом
     */
    fun getWordsObservable(): Observable<List<Word>> {
        return repository.getWordsObservable()
    }

    /**
     * Выполнить поиск среди слов.
     *
     * @param search поисковый запрос
     */
    fun searchWords(search: String) {
        repository.search(search)
    }

    /**
     * Перевести и сохранить новое слово.
     *
     * @param word слово для перевода
     * @param translation направление перевода
     * @return [Completable] с результатом операции
     */
    fun translateAndSave(word: String, translation: Translation): Completable {
        return repository.translate(word, translation)
            .flatMapCompletable(repository::saveWord)
    }

    /**
     * Инвертировать состояние избранного у слова.
     *
     * @param word слово для изменения
     * @return [Completable] с результатом операции
     */
    fun switchFavorite(word: Word): Completable {
        val newWord = word.copy(favorite = !word.favorite)
        Log.d(TAG, "oldWord = $word; newWord = $newWord")
        return repository.updateWord(newWord)
    }

    /**
     * Получить список избранных слов.
     *
     * @return список избранных слов
     */
    fun getFavoriteWords(): Single<List<Word>> {
        return repository.getFavoriteWords()
    }
}