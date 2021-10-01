package org.maxsur.mydictionary.data.converter

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.maxsur.mydictionary.data.model.local.WordEntity
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word

class WordToWordEntityConverterTest {

    private val converter = WordToWordEntityConverter()

    @Test
    fun convert() {
        val word = Word("house", "дом", Translation("en", "ru"), true)
        val expected = WordEntity(0, "house", "дом", "en", "ru", true)
        val actual = converter.convert(word)
        assertThat(actual).isEqualTo(expected)
    }
}