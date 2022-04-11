package org.maxsur.mydictionary.data.repository

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.maxsur.mydictionary.BuildConfig
import org.maxsur.mydictionary.data.converter.TranslateResponseToWordConverter
import org.maxsur.mydictionary.data.converter.TranslateResponseToWordConverter.ConverterArgs
import org.maxsur.mydictionary.data.converter.WordEntityToWordConverter
import org.maxsur.mydictionary.data.converter.WordToWordEntityConverter
import org.maxsur.mydictionary.data.dao.DictionaryDao
import org.maxsur.mydictionary.data.model.remote.TranslateRequestBody
import org.maxsur.mydictionary.data.service.DictionaryService
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.domain.repository.DictionaryRepository

const val TAG = "DictionaryRepository"

/**
 * Репозиторий словаря.
 *
 * @property dictionaryService сервис для доступа к переводчику
 * @property dictionaryDao интерфейс для доступа к БД словаря
 * @property translateResponseToWordConverter конвертер ответа в доменную модель
 * @property wordToWordEntityConverter конверетер доменной модели в сущность БД
 * @property wordEntityToWordConverter конвертер списка сущностей БД в список доменных моделей
 */
class DictionaryRepositoryImpl(
    private val dictionaryService: DictionaryService,
    private val dictionaryDao: DictionaryDao,
    private val translateResponseToWordConverter: TranslateResponseToWordConverter,
    private val wordToWordEntityConverter: WordToWordEntityConverter,
    private val wordEntityToWordConverter: WordEntityToWordConverter
) : DictionaryRepository {

    private val searchSubject = PublishSubject.create<String>()

    override fun getWordsObservable(): Observable<List<Word>> {
        return searchSubject.startWith("")
            .switchMap { search ->
                if (search.isBlank()) {
                    dictionaryDao.getAllWordsObservable()
                } else {
                    dictionaryDao.getSearchObservable(search)
                }
            }
            .map(wordEntityToWordConverter::convertList)
            .doOnNext{
                Log.d(TAG, "getWordsObservable: $it")
            }
    }

    override fun search(search: String) {
        searchSubject.onNext(search)
    }

    override fun translate(word: String, translation: Translation): Single<Word> {
        return dictionaryService.translate(
            TranslateRequestBody(
                folderId = BuildConfig.folderId,
                texts = listOf(word),
                sourceLanguageCode = translation.from,
                targetLanguageCode = translation.to
            )
        ).map { response ->
            translateResponseToWordConverter.convert(response, ConverterArgs(word, translation))
        }
    }

    override fun saveWord(word: Word): Completable {
        return dictionaryDao.saveWord(wordToWordEntityConverter.convert(word))
    }

    override fun updateWord(word: Word): Completable {
        return dictionaryDao.updateWord(wordToWordEntityConverter.convert(word))
    }

    override fun getFavoriteWords(): Single<List<Word>> {
        return dictionaryDao.getFavoriteWords()
            .map(wordEntityToWordConverter::convertList)
    }
}