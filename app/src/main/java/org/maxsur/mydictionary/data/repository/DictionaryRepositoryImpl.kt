package org.maxsur.mydictionary.data.repository

import io.reactivex.Single
import org.maxsur.mydictionary.BuildConfig
import org.maxsur.mydictionary.data.converter.TranslateResponseToWordConverter
import org.maxsur.mydictionary.data.converter.TranslateResponseToWordConverter.ConverterArgs
import org.maxsur.mydictionary.data.converter.WordEntityListToWordListConverter
import org.maxsur.mydictionary.data.converter.WordToWordEntityConverter
import org.maxsur.mydictionary.data.dao.DictionaryDao
import org.maxsur.mydictionary.data.model.remote.TranslateRequestBody
import org.maxsur.mydictionary.data.service.DictionaryService
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.domain.repository.DictionaryRepository

/**
 * Репозиторий словаря.
 *
 * @property dictionaryService сервис для доступа к переводчику
 * @property dictionaryDao интерфейс для доступа к БД словаря
 * @property translateResponseToWordConverter конвертер ответа в доменную модель
 * @property wordToWordEntityConverter конверетер доменной модели в сущность БД
 * @property wordEntityListToWordListConverter конвертер списка сущностей БД в список доменных моделей
 */
class DictionaryRepositoryImpl(
    private val dictionaryService: DictionaryService,
    private val dictionaryDao: DictionaryDao,
    private val translateResponseToWordConverter: TranslateResponseToWordConverter,
    private val wordToWordEntityConverter: WordToWordEntityConverter,
    private val wordEntityListToWordListConverter: WordEntityListToWordListConverter
) : DictionaryRepository {

    override fun getWords(search: String?): Single<List<Word>> {
        return if (search != null) {
            dictionaryDao.searchWords(search)
        } else {
            dictionaryDao.getAllWords()
        }.map(wordEntityListToWordListConverter::convert)
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

    override fun saveWord(word: Word) {
        dictionaryDao.saveWord(wordToWordEntityConverter.convert(word))
    }
}