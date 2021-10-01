package org.maxsur.mydictionary.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность слова в базе данных.
 *
 * @property uid уникальный ID
 * @property original оригинальное слово
 * @property translated переведенное слово
 * @property fromLang язык оригинала
 * @property toLang язык перевода
 * @property favorite признак избранного
 */
@Entity(tableName = "word")
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "original") val original: String,
    @ColumnInfo(name = "translated") val translated: String,
    @ColumnInfo(name = "lang_from") val fromLang: String,
    @ColumnInfo(name = "lang_to") val toLang: String,
    @ColumnInfo(name = "favorite") val favorite: Boolean = false
)
