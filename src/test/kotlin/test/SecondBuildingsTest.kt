package test

import homework.lesson1.SecondBuildings
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SecondBuildingsTest {

    @Test
    fun `вычисление цены со скидкой для вторички, если цена меньше или равна скидке`() {
        val secondBuildings = SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 50000u)
        assertEquals("""price with sale: 50000 
            |Скидка не применена, т.к. цена не может быть меньше скидки
            |Выше указана новая цена""".trimMargin(), secondBuildings.discountInfo())
    }

    @Test
    fun `вычисление цены со скидкой для вторички, если цена равна 0`() {
        val secondBuildings = SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 0u)
        assertEquals("""price with sale: 50000 
            |Скидка не применена, т.к. цена не может быть меньше скидки
            |Выше указана новая цена""".trimMargin(), secondBuildings.discountInfo())
    }

    @Test
    fun `вычисление цены со скидкой для вторички, если цена равна 4,294,967,295`() {
        val secondBuildings = SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 4294967295u)
        assertEquals("price with sale: 4294917295 ", secondBuildings.discountInfo())
    }

    @Test
    fun `вычисление цены со скидкой для вторички, если цена равна 1,500,000`() {
        val secondBuildings = SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 1500000u)
        assertEquals("price with sale: 1450000 ", secondBuildings.discountInfo())
    }
}