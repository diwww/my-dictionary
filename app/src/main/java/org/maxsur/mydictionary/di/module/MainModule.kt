package org.maxsur.mydictionary.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.maxsur.mydictionary.BuildConfig
import org.maxsur.mydictionary.data.converter.TranslateResponseToWordConverter
import org.maxsur.mydictionary.data.converter.WordEntityToWordConverter
import org.maxsur.mydictionary.data.converter.WordToWordEntityConverter
import org.maxsur.mydictionary.data.database.DictionaryDatabase
import org.maxsur.mydictionary.data.repository.DictionaryRepositoryImpl
import org.maxsur.mydictionary.data.repository.DictionaryRepositoryStub
import org.maxsur.mydictionary.data.service.DictionaryService
import org.maxsur.mydictionary.domain.interactor.DictionaryInteractor
import org.maxsur.mydictionary.domain.repository.DictionaryRepository
import org.maxsur.mydictionary.presentation.presenter.dictionary.DictionaryPresenter
import org.maxsur.mydictionary.util.RxSchedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
object MainModule {

    @JvmStatic
    @Provides
    fun providePresenter(
        interactor: DictionaryInteractor,
        rxSchedulers: RxSchedulers
    ): DictionaryPresenter {
        return DictionaryPresenter(interactor, rxSchedulers)
    }

    @JvmStatic
    @Provides
    fun provideInteractor(repository: DictionaryRepository): DictionaryInteractor {
        return DictionaryInteractor(repository)
    }

    @JvmStatic
    @Provides
    fun provideRepository(
        dictionaryService: DictionaryService,
        dictionaryDatabase: DictionaryDatabase
    ): DictionaryRepository {
        return if (BuildConfig.useStubRepository) {
            DictionaryRepositoryStub()
        } else {
            DictionaryRepositoryImpl(
                dictionaryService,
                dictionaryDatabase.dictionaryDao(),
                TranslateResponseToWordConverter(),
                WordToWordEntityConverter(),
                WordEntityToWordConverter()
            )
        }
    }

    @JvmStatic
    @Provides
    fun provideRxSchedulers(): RxSchedulers {
        return RxSchedulers()
    }

    @JvmStatic
    @Provides
    fun provideDictionaryService(): DictionaryService {
        val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer ${BuildConfig.token}")
                .build()
            chain.proceed(request)
        }.build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://translate.api.cloud.yandex.net/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(DictionaryService::class.java)
    }
}