package org.maxsur.mydictionary.data.repository

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.maxsur.mydictionary.data.converter.TranslateResponseToWordConverter
import org.maxsur.mydictionary.data.converter.WordEntityListToWordListConverter
import org.maxsur.mydictionary.data.converter.WordToWordEntityConverter
import org.maxsur.mydictionary.data.dao.DictionaryDao
import org.maxsur.mydictionary.data.model.local.WordEntity
import org.maxsur.mydictionary.data.model.remote.TranslateResponse
import org.maxsur.mydictionary.data.service.DictionaryService
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import java.lang.RuntimeException

class DictionaryRepositoryImplTest {

    @MockK
    private lateinit var dictionaryService: DictionaryService

    @MockK
    private lateinit var dictionaryDao: DictionaryDao

    @MockK
    private lateinit var translateResponseToWordConverter: TranslateResponseToWordConverter

    @MockK
    private lateinit var wordToWordEntityConverter: WordToWordEntityConverter

    @MockK
    private lateinit var wordEntityListToWordListConverter: WordEntityListToWordListConverter

    @InjectMockKs
    private lateinit var repository: DictionaryRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `getWords when search is null`() {
        val wordEntities = listOf(
            WordEntity(0, "дом", "house", "ru", "en"),
            WordEntity(1, "dog", "собака", "en", "ru")
        )
        val words = listOf(
            Word("дом", "house", Translation("ru", "en")),
            Word("dog", "собака", Translation("en", "ru"))
        )
        every { dictionaryDao.getAllWords() } returns Single.just(wordEntities)
        every { wordEntityListToWordListConverter.convert(wordEntities) } returns words

        repository.getWords(null)
            .test()
            .assertNoErrors()
            .assertValue(words)
    }

    @Test
    fun `getWords when search is not null`() {
        val search = "abc"
        val wordEntities = listOf(
            WordEntity(0, "дом", "house", "ru", "en"),
            WordEntity(1, "dog", "собака", "en", "ru")
        )
        val words = listOf(
            Word("дом", "house", Translation("ru", "en")),
            Word("dog", "собака", Translation("en", "ru"))
        )
        every { dictionaryDao.searchWords(search) } returns Single.just(wordEntities)
        every { wordEntityListToWordListConverter.convert(wordEntities) } returns words

        repository.getWords(search)
            .test()
            .assertNoErrors()
            .assertValue(words)
    }

    @Test
    fun `translate success`() {
        val response = TranslateResponse(listOf(TranslateResponse.Translation("дом", "en")))
        val word = Word("house", "дом", Translation("en", "ru"))
        every { dictionaryService.translate(any()) } returns Single.just(response)
        every {
            translateResponseToWordConverter.convert(
                response,
                TranslateResponseToWordConverter.ConverterArgs("house", Translation("en", "ru"))
            )
        } returns word

        repository.translate("house", Translation("en", "ru"))
            .test()
            .assertNoErrors()
            .assertValue(word)
    }

    @Test
    fun `translate error`() {
        val exception = RuntimeException()
        every { dictionaryService.translate(any()) } returns Single.error(exception)

        repository.translate("house", Translation("en", "ru"))
            .test()
            .assertNoValues()
            .assertError(exception)
    }

    @Test
    fun saveWord() {
        val word = Word("house", "дом", Translation("en", "ru"))
        val wordEntity = WordEntity(0, "house", "дом", "en", "ru")
        every { wordToWordEntityConverter.convert(word) } returns wordEntity

        repository.saveWord(word)

        verify { dictionaryDao.saveWord(wordEntity) }
    }
}