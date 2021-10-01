package org.maxsur.mydictionary.data.model.remote

/**
 * Тело запроса перевода слов.
 *
 * @param folderId идентификатор каталога
 * @param texts список слов для перевода
 * @param sourceLanguageCode язык оригинала
 * @param targetLanguageCode язык перевода
 */
data class TranslateRequestBody(
    val folderId: String,
    val texts: List<String>,
    val sourceLanguageCode: String,
    val targetLanguageCode: String
)
