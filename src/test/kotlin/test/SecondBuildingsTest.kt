package test

import homework.lesson1.SecondBuildings
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SecondBuildingsTest {

    @ParameterizedTest
    @CsvSource("50000", "70000", "100000")
    fun `вычисление цены со скидкой для вторички (финальная цена меньше или равна скидке)`(
        price: UInt,
    ) {
        val secondBuildings = SecondBuildings("г. Рязань, ул. Ленина, 8, 34", price)
        assertEquals(listOf("Price with sale: 50000",
            "Final price is less than or equal to the minimum price",
            "The minimum possible price is set").joinToString("\n"),
            secondBuildings.discountInfo())
    }

    @Test
    fun `вычисление цены со скидкой для вторички (цена равна 0)`() {
        val secondBuildings = SecondBuildings("г. Рязань, ул. Ленина, 8, 34", UInt.MIN_VALUE)
        assertEquals(listOf("Price with sale: 50000",
            "The discount does not apply, because the price cannot be less than the discount",
            "The minimum possible price is set").joinToString("\n"),
            secondBuildings.discountInfo())
    }

    @Test
    fun `вычисление цены со скидкой для вторички (цена максимальная - 4,294,967,295`() {
        val secondBuildings = SecondBuildings("г. Рязань, ул. Ленина, 8, 34", UInt.MAX_VALUE)
        assertEquals("Price with sale: 4294917295", secondBuildings.discountInfo())
    }


}