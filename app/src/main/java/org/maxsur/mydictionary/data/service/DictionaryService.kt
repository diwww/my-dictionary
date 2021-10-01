package org.maxsur.mydictionary.data.service

import io.reactivex.Single
import org.maxsur.mydictionary.data.model.remote.TranslateRequestBody
import org.maxsur.mydictionary.data.model.remote.TranslateResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Интерфейс для доступа к переводчику.
 */
interface DictionaryService {

    @POST("translate/v2/translate")
    fun translate(@Body body: TranslateRequestBody): Single<TranslateResponse>
}