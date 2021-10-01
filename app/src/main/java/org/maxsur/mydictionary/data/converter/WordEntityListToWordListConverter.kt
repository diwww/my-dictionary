package org.maxsur.mydictionary.data.converter

import org.maxsur.mydictionary.data.model.local.WordEntity
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word

/**
 * Конвертер списка сущностей БД [WordEntity] в список доменных моделей [Word].
 */
class WordEntityListToWordListConverter : Converter<List<WordEntity>, List<Word>, Any> {

    override fun convert(from: List<WordEntity>, args: Any?): List<Word> {
        return from.map {
            Word(
                original = it.original,
                translated = it.translated,
                translation = Translation(it.fromLang, it.toLang),
                favorite = it.favorite
            )
        }
    }
}