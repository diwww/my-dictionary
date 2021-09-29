package org.maxsur.mydictionary.domain.model

/**
 * Сущность слова.
 *
 * @property original исходное слово
 * @property translated перевод
 * @property translation направление перевода
 * @property favorite признак того, что слово добавлено в избранное
 */
data class Word(
    val original: String,
    val translated: String,
    val translation: Translation,
    val favorite: Boolean = false
)
