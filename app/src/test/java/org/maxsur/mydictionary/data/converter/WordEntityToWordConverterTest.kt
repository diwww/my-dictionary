package org.maxsur.mydictionary.data.converter


import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.maxsur.mydictionary.data.model.local.WordEntity
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word

class WordEntityToWordConverterTest {

    private val converter = WordEntityToWordConverter()

    @Test
    fun convert() {
        val wordEntity =
            WordEntity(0, "house", "дом", "en", "ru", true)
        val expected = Word("house", "дом", Translation("en", "ru"), true)
        val actual = converter.convert(wordEntity)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun convertList() {
        val wordEntityList = listOf(
            WordEntity(0, "house", "дом", "en", "ru", true)
        )
        val expected = listOf(Word("house", "дом", Translation("en", "ru"), true))
        val actual = converter.convertList(wordEntityList)
        assertThat(actual).isEqualTo(expected)
    }
}