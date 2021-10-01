package org.maxsur.mydictionary.data.model.remote

/**
 * Сущность ответа перевода слов.
 *
 * @property translations список переводов
 */
data class TranslateResponse(val translations: List<Translation>) {

    /**
     * Сущность перевода.
     *
     * @property text текст перевода
     * @property detectedLanguageCode код языка исходного текста
     */
    data class Translation(val text: String, val detectedLanguageCode: String)
}
