package org.maxsur.mydictionary.data.converter

import org.maxsur.mydictionary.data.model.local.WordEntity
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word

class WordEntityToWordConverter : Converter<WordEntity, Word, Any> {

    override fun convert(from: WordEntity, args: Any?): Word {
        return Word(
            original = from.original,
            translated = from.translated,
            translation = Translation(from.fromLang, from.toLang),
            favorite = from.favorite,
            id = from.uid
        )
    }
}