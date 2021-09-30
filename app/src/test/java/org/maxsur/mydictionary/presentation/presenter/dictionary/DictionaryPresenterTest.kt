package org.maxsur.mydictionary.presentation.presenter.dictionary

import android.util.Log
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.maxsur.mydictionary.domain.interactor.DictionaryInteractor
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word
import org.maxsur.mydictionary.presentation.view.dictionary.DictionaryView
import org.maxsur.mydictionary.util.RxSchedulers
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

private const val DEBOUNCE_MS = 500L
private const val MS_300 = 300L

class DictionaryPresenterTest {

    @MockK
    private lateinit var interactor: DictionaryInteractor

    @MockK
    private lateinit var rxSchedulers: RxSchedulers

    @MockK
    private lateinit var view: DictionaryView

    @InjectMockKs
    private lateinit var presenter: DictionaryPresenter

    private val testScheduler = TestScheduler()


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0

        every { rxSchedulers.io } returns testScheduler
        every { rxSchedulers.computation } returns testScheduler
        every { rxSchedulers.main } returns testScheduler
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `onFirstViewAttach success`() {
        val words = listOf(
            Word("дом", "house", Translation("RU", "EN")),
            Word("dog", "собака", Translation("EN", "RU"))
        )
        every { interactor.getAllWords() } returns Single.just(words)

        presenter.attachView(view)
        testScheduler.triggerActions()

        verify { view.showWords(words) }
        verify(exactly = 0) { view.showError() }
    }

    @Test
    fun `onFirstViewAttach error`() {
        val exception = RuntimeException()
        every { interactor.getAllWords() } returns Single.error(exception)

        presenter.attachView(view)
        testScheduler.triggerActions()

        verify(exactly = 0) { view.showWords(any()) }
        verify { Log.e(any(), exception.message, exception) }
        verify { view.showError() }
    }

    @Test
    fun `searchWords success`() {
        val search = "abc"
        val foundWords = listOf(
            Word("дом", "house", Translation("RU", "EN"))
        )
        every { interactor.getAllWords() } returns Single.just(emptyList())
        every { interactor.searchWords(search) } returns Single.just(foundWords)

        presenter.attachView(view)
        presenter.searchWords(search)
        testScheduler.advanceTimeBy(DEBOUNCE_MS, TimeUnit.MILLISECONDS)
        testScheduler.triggerActions()

        verify { view.showWords(foundWords) }
        verify(exactly = 0) { view.showError() }
    }

    @Test
    fun `searchWords test debounce`() {
        val search1 = "abc"
        val search2 = "def"
        val foundWords1 = listOf(
            Word("дом", "house", Translation("RU", "EN"))
        )
        val foundWords2 = listOf(
            Word("dog", "собака", Translation("EN", "RU"))
        )
        every { interactor.getAllWords() } returns Single.just(emptyList())
        every { interactor.searchWords(search1) } returns Single.just(foundWords1)
        every { interactor.searchWords(search2) } returns Single.just(foundWords2)

        presenter.attachView(view)
        presenter.searchWords(search1)
        testScheduler.advanceTimeBy(MS_300, TimeUnit.MILLISECONDS)
        presenter.searchWords(search2)
        testScheduler.advanceTimeBy(DEBOUNCE_MS, TimeUnit.MILLISECONDS)
        testScheduler.triggerActions()

        verify { view.showWords(foundWords2) }
        verify(exactly = 0) { view.showError() }
    }

    @Test
    fun `searchWords error`() {
        val search = "abc"
        val words = listOf(
            Word("дом", "house", Translation("RU", "EN")),
            Word("dog", "собака", Translation("EN", "RU"))
        )
        val exception = RuntimeException()
        every { interactor.getAllWords() } returns Single.just(emptyList())
        every { interactor.searchWords(search) } returns Single.error(exception)

        presenter.attachView(view)
        testScheduler.triggerActions()

        every { interactor.getAllWords() } returns Single.just(words)
        presenter.searchWords(search)
        testScheduler.advanceTimeBy(DEBOUNCE_MS, TimeUnit.MILLISECONDS)
        testScheduler.triggerActions()

        verify { Log.e(any(), exception.message, exception) }
        verify { view.showWords(words) }
        verify(exactly = 0) { view.showError() }
    }

    @Test
    fun `translate success`() {
        val words = listOf(
            Word("дом", "house", Translation("RU", "EN")),
            Word("dog", "собака", Translation("EN", "RU"))
        )
        val word = "abc"
        val from = "EN"
        val to = "RU"
        every { interactor.getAllWords() } returns Single.just(emptyList())
        every {
            interactor.translateAndSave(word, Translation(from, to))
        } returns Single.just(words)

        presenter.attachView(view)
        presenter.translate(word, from, to)
        testScheduler.triggerActions()

        verify { view.showProgress(true) }
        verify { view.showProgress(false) }
        verify { view.setSearchText("") }
        verify { view.showWords(words) }
        verify(exactly = 0) { view.showError() }
    }

    @Test
    fun `translate when word is blank`() {
        val words = listOf(
            Word("дом", "house", Translation("RU", "EN")),
            Word("dog", "собака", Translation("EN", "RU"))
        )
        val word = "   "
        val from = "EN"
        val to = "RU"
        every { interactor.getAllWords() } returns Single.just(emptyList())
        every {
            interactor.translateAndSave(word, Translation(from, to))
        } returns Single.just(words)

        presenter.attachView(view)
        presenter.translate(word, from, to)
        testScheduler.triggerActions()

        verify(exactly = 0) { view.showProgress(true) }
        verify(exactly = 0) { view.showProgress(false) }
        verify(exactly = 0) { view.setSearchText("") }
        verify(exactly = 0) { view.showWords(words) }
        verify(exactly = 0) { view.showError() }
    }

    @Test
    fun `translate error`() {
        val words = listOf(
            Word("дом", "house", Translation("RU", "EN")),
            Word("dog", "собака", Translation("EN", "RU"))
        )
        val word = "abc"
        val from = "EN"
        val to = "RU"
        val exception = RuntimeException()
        every { interactor.getAllWords() } returns Single.just(emptyList())
        every {
            interactor.translateAndSave(word, Translation(from, to))
        } returns Single.error(exception)

        presenter.attachView(view)
        presenter.translate(word, from, to)
        testScheduler.triggerActions()

        verify { view.showProgress(true) }
        verify { view.showProgress(false) }
        verify(exactly = 0) { view.setSearchText("") }
        verify(exactly = 0) { view.showWords(words) }
        verify { Log.e(any(), exception.message, exception) }
        verify { view.showError() }
    }

    @Test
    fun reverseLanguages() {
        val from = 0
        val to = 1
        every { interactor.getAllWords() } returns Single.just(emptyList())
        presenter.attachView(view)
        presenter.reverseLanguages(from, to)

        verify { view.setSpinnersSelection(to, from) }
    }
}