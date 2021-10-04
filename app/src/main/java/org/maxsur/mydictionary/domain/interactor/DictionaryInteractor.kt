package org.maxsur.mydictionary.domain.interactor

import io.reactivex.Single
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.domain.repository.DictionaryRepository

/**
 * Интерактор для работы со словарем.
 *
 * @property repository репозиторий для работы со словарем
 */
class DictionaryInteractor(private val repository: DictionaryRepository) {

    /**
     * Получить все слова с переводом.
     *
     * @return список слов с переводом
     */
    fun getAllWords(): Single<List<Word>> {
        return repository.getWords()
    }

    /**
     * Выполнить поиск среди слов.
     *
     * @param search поисковый запрос
     * @return отфильтрованный список слов с переводом
     */
    fun searchWords(search: String): Single<List<Word>> {
        return repository.getWords(search)
    }

    /**
     * Перевести и сохранить новое слово.
     *
     * @param word слово для перевода
     * @param translation направление перевода
     * @return обновленный список слов с переводом, включающий новое слово
     */
    fun translateAndSave(word: String, translation: Translation): Single<List<Word>> {
        return repository.translate(word, translation)
            .flatMapCompletable(repository::saveWord)
            .andThen(repository.getWords())
    }

    /**
     * Инвертировать состояние избранного у слова.
     *
     * @param word слово для изменения
     * @return слово с инвертированным состоянием избранного
     */
    fun switchFavorite(word: Word): Single<Word> {
        return repository.updateWord(word.copy(favorite = !word.favorite))
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