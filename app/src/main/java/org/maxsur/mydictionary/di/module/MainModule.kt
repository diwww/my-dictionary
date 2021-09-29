package org.maxsur.mydictionary.di.module

import dagger.Module
import dagger.Provides
import org.maxsur.mydictionary.data.repository.DictionaryRepositoryStub
import org.maxsur.mydictionary.domain.interactor.DictionaryInteractor
import org.maxsur.mydictionary.domain.repository.DictionaryRepository
import javax.inject.Singleton

@Module
object MainModule {

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