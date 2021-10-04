package org.maxsur.mydictionary.data.repository

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.maxsur.mydictionary.data.converter.TranslateResponseToWordConverter
import org.maxsur.mydictionary.data.converter.WordEntityToWordConverter
import org.maxsur.mydictionary.data.converter.WordToWordEntityConverter
import org.maxsur.mydictionary.data.dao.DictionaryDao
import org.maxsur.mydictionary.data.model.local.WordEntity
import org.maxsur.mydictionary.data.model.remote.TranslateResponse
import org.maxsur.mydictionary.data.service.DictionaryService
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word

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
    private lateinit var wordEntityToWordConverter: WordEntityToWordConverter

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
        every { wordEntityToWordConverter.convertList(wordEntities) } returns words

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
        every { wordEntityToWordConverter.convertList(wordEntities) } returns words

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
        every { dictionaryDao.saveWord(wordEntity) } returns Completable.complete()

        repository.saveWord(word)
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun updateWord() {
        val word = Word("house", "дом", Translation("en", "ru"), id = 0)
        val wordEntity = WordEntity(0, "house", "дом", "en", "to")
        every { wordToWordEntityConverter.convert(word) } returns wordEntity
        every { wordEntityToWordConverter.convert(wordEntity) } returns word
        every { dictionaryDao.updateWord(wordEntity) } returns Completable.complete()
        every { dictionaryDao.getWord(0) } returns Single.just(wordEntity)

        repository.updateWord(word)
            .test()
            .assertNoErrors()
            .assertValue(word)
    }

    @Test
    fun getFavoriteWords() {
        val wordEntities = listOf(
            WordEntity(0, "дом", "house", "ru", "en", favorite = true),
            WordEntity(1, "dog", "собака", "en", "ru", favorite = true)
        )
        val words = listOf(
            Word("дом", "house", Translation("ru", "en"), favorite = true),
            Word("dog", "собака", Translation("en", "ru"), favorite = true)
        )
        every { dictionaryDao.getFavoriteWords() } returns Single.just(wordEntities)
        every { wordEntityToWordConverter.convertList(wordEntities) } returns words

        repository.getFavoriteWords()
            .test()
            .assertNoErrors()
            .assertValue(words)
    }
}