package org.maxsur.mydictionary.domain.interactor


import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.domain.repository.DictionaryRepository

class DictionaryInteractorTest {

    @MockK
    private lateinit var repository: DictionaryRepository

    @InjectMockKs
    private lateinit var interactor: DictionaryInteractor

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getAllWords() {
        val words = listOf(
            Word("дом", "house", Translation("RU", "EN")),
            Word("dog", "собака", Translation("EN", "RU"))
        )
        every { repository.getWords() } returns Single.just(words)

        interactor.getAllWords()
            .test()
            .assertNoErrors()
            .assertValue(words)
        verify { repository.getWords() }
    }

    @Test
    fun getAllWords_Error() {
        val exception = RuntimeException()
        every { repository.getWords() } returns Single.error(exception)

        interactor.getAllWords()
            .test()
            .assertNoValues()
            .assertError(exception)
        verify { repository.getWords() }
    }

    @Test
    fun searchWords() {
        val search = "abc"
        val words = listOf(
            Word("дом", "house", Translation("RU", "EN")),
            Word("dog", "собака", Translation("EN", "RU"))
        )
        every { repository.getWords(search) } returns Single.just(words)

        interactor.searchWords(search)
            .test()
            .assertNoErrors()
            .assertValue(words)
        verify { repository.getWords(search) }
    }

    @Test
    fun searchWords_Error() {
        val search = "abc"
        val exception = RuntimeException()
        every { repository.getWords(search) } returns Single.error(exception)

        interactor.searchWords(search)
            .test()
            .assertNoValues()
            .assertError(exception)
        verify { repository.getWords(search) }
    }

    @Test
    fun translateAndSave() {
        val toTranslate = "abc"
        val translation = Translation("EN", "RU")
        val newWord = Word("river", "река", translation)
        val words = listOf(
            Word("дом", "house", Translation("RU", "EN")),
            Word("dog", "собака", Translation("EN", "RU"))
        )
        val newWords = words.plus(newWord)
        every { repository.translate(toTranslate, translation) } returns Single.just(newWord)
        every { repository.saveWord(newWord) } returns Completable.complete()
        every { repository.getWords() } returns Single.just(newWords)

        interactor.translateAndSave(toTranslate, translation)
            .test()
            .assertNoErrors()
            .assertValue(newWords)
        verify { repository.translate(toTranslate, translation) }
        verify { repository.saveWord(newWord) }
        verify { repository.getWords() }
    }

    @Test
    fun translateAndSave_TranslateError() {
        val toTranslate = "abc"
        val translation = Translation("EN", "RU")
        val exception = RuntimeException()
        every { repository.translate(toTranslate, translation) } returns Single.error(exception)
        every { repository.getWords() } returns Single.just(emptyList())

        interactor.translateAndSave(toTranslate, translation)
            .test()
            .assertNoValues()
            .assertError(exception)
        verify { repository.translate(toTranslate, translation) }
    }

    @Test
    fun translateAndSave_SaveError() {
        val toTranslate = "abc"
        val translation = Translation("EN", "RU")
        val newWord = Word("river", "река", translation)
        val exception = RuntimeException()
        every { repository.translate(toTranslate, translation) } returns Single.just(newWord)
        every { repository.saveWord(newWord) } returns Completable.error(exception)
        every { repository.getWords() } returns Single.just(emptyList())

        interactor.translateAndSave(toTranslate, translation)
            .test()
            .assertNoValues()
            .assertError(exception)

        verify { repository.translate(toTranslate, translation) }
        verify { repository.saveWord(newWord) }
    }

    @Test
    fun translateAndSave_GetWordsError() {
        val toTranslate = "abc"
        val translation = Translation("EN", "RU")
        val newWord = Word("river", "река", translation)
        val exception = RuntimeException()
        every { repository.translate(toTranslate, translation) } returns Single.just(newWord)
        every { repository.saveWord(newWord) } returns Completable.complete()
        every { repository.getWords() } returns Single.error(exception)

        interactor.translateAndSave(toTranslate, translation)
            .test()
            .assertNoValues()
            .assertError(exception)
        verify { repository.translate(toTranslate, translation) }
        verify { repository.saveWord(newWord) }
        verify { repository.getWords() }
    }

    @Test
    fun switchFavorite() {
        val originalWord = Word("river", "река", Translation("EN", "RU"), favorite = true, id = 123)
        val newWord = Word("river", "река", Translation("EN", "RU"), favorite = false, id = 123)
        every { repository.updateWord(newWord) } returns Single.just(newWord)

        interactor.switchFavorite(originalWord)
            .test()
            .assertValue(newWord)
            .assertNoErrors()
    }

    @Test
    fun getFavoriteWords() {
        val words = listOf(
            Word("дом", "house", Translation("RU", "EN")),
            Word("dog", "собака", Translation("EN", "RU"))
        )
        every { repository.getFavoriteWords() } returns Single.just(words)

        interactor.getFavoriteWords()
            .test()
            .assertValue(words)
            .assertNoErrors()
    }
}