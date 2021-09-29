package org.maxsur.mydictionary.domain.model

/**
 * Сущность слова.
 *
 * @property original исходное слово
 * @property translation перевод
 * @property favorite признак того, что слово добавлено в избранное
 */
data class Word(
    val original: String,
    val translation: String,
    val favorite: Boolean = false
)
