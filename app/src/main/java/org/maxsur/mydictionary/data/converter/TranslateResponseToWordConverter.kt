package org.maxsur.mydictionary.data.converter

import org.maxsur.mydictionary.data.model.remote.TranslateResponse
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word

/**
 * Конвертер ответа переводчика [TranslateResponse] в доменную модель слова [Word].
 */
class TranslateResponseToWordConverter :
    Converter<TranslateResponse, Word, TranslateResponseToWordConverter.ConverterArgs> {

    override fun convert(from: TranslateResponse, args: ConverterArgs?): Word {
        return Word(
            original = args!!.originalWord.trim(),
            translated = from.translations.first().text.trim(),
            translation = args.translation
        )
    }

    /**
     * Аргументы конвертера.
     *
     * @property originalWord оригинальное слово
     * @property направление перевода
     */
    data class ConverterArgs(val originalWord: String, val translation: Translation)
}