package org.maxsur.mydictionary.data.converter

/**
 * Интерфейс конвертера.
 *
 * @param F откуда
 * @param T куда
 * @param A тип аргументов
 */
interface Converter<F, T, A> {

    fun convert(from: F, args: A? = null): T

    fun convertList(from: List<F>, args: A? = null): List<T> = from.map(this::convert)
}