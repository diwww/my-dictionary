package org.maxsur.mydictionary.data.converter

import org.maxsur.mydictionary.data.model.local.WordEntity
import org.maxsur.mydictionary.domain.model.Word

/**
 * Конвертер доменной  модели [Word] в сущность БД [WordEntity].
 */
class WordToWordEntityConverter : Converter<Word, WordEntity, Any> {

    override fun convert(from: Word, args: Any?): WordEntity {
        return WordEntity(
            original = from.original,
            translated = from.translated,
            fromLang = from.translation.from,
            toLang = from.translation.to,
            favorite = from.favorite
        )
    }
}