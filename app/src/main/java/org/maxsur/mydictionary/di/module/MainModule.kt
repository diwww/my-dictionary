package org.maxsur.mydictionary.di.module

import dagger.Module
import dagger.Provides
import org.maxsur.mydictionary.data.repository.DictionaryRepositoryStub
import org.maxsur.mydictionary.domain.interactor.DictionaryInteractor
import org.maxsur.mydictionary.domain.repository.DictionaryRepository
import org.maxsur.mydictionary.presentation.presenter.dictionary.DictionaryPresenter

@Module
object MainModule {

    @JvmStatic
    @Provides
    fun providePresenter(interactor: DictionaryInteractor):DictionaryPresenter{
        return DictionaryPresenter(interactor)
    }

    @JvmStatic
    @Provides
    fun provideInteractor(repository: DictionaryRepository): DictionaryInteractor {
        return DictionaryInteractor(repository)
    }

    @JvmStatic
    @Provides
    fun provideRepository(): DictionaryRepository {
        return DictionaryRepositoryStub()
    }
}