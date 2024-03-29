package org.maxsur.mydictionary.domain.model

/**
 * Сущность направления перевода.
 *
 * @property from язык, с которого осуществляется перевод
 * @property to  язык, на который осуществляется перевод
 */
data class Translation(
    val from: String,
    val to: String
) {

    /**
     * Инвертировать направление перевода.
     *
     * @return сущность [Translation] с инвертированным направлением перевода.
     */
    fun reverse() = Translation(to, from)
}
