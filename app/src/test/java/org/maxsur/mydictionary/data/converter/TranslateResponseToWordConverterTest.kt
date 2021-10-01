package org.maxsur.mydictionary.data.converter

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.maxsur.mydictionary.data.model.remote.TranslateResponse
import org.maxsur.mydictionary.domain.model.Translation
import org.maxsur.mydictionary.domain.model.Word

class TranslateResponseToWordConverterTest {

    private val converter = TranslateResponseToWordConverter()

    @Test
    fun convert() {
        val response = TranslateResponse(listOf(TranslateResponse.Translation("дом", "en")))
        val expected = Word("house", "дом", Translation("en", "ru"))
        val actual = converter.convert(
            response, TranslateResponseToWordConverter.ConverterArgs(
                "house",
                Translation("en", "ru")
            )
        )
        assertThat(actual).isEqualTo(expected)
    }
}