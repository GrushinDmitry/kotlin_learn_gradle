package test

import homework.lesson1.NewBuildings
import io.mockk.clearAllMocks
import io.mockk.impl.annotations.SpyK
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class NewBuildingsTest {

    @SpyK
    var newBuildings = NewBuildings("г. Рязань, ул. Почтовая, 20, 100", 100000u)

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `тестируем discountInfo() newBuilding`() {
        newBuildings.discountInfo()
        assertEquals("No sale for new building", newBuildings.discountInfo())
    }


    @ParameterizedTest
    @CsvSource("0", "50000")
    fun `вычисление цены для новостройки (финальная цена меньше или равна минимальной)`(price: UInt) {
        newBuildings = spyk(NewBuildings("г. Рязань, ул. Почтовая, 20, 100", price))
        assertEquals(listOf("Final price new building with rising: 150000",
            "Final price is less than or equal to the minimum price",
            "The minimum possible price is set").joinToString("\n"),
            newBuildings.finalPriceInfo())
    }

    @Test
    fun `вычисление цены для новостройки (финальная цена больше минимальной и меньше или равна максимальной)`() {
        val listPrice = listOf(50001u, UInt.MAX_VALUE - 100000u)
        for (price in listPrice) {
            newBuildings = spyk(NewBuildings("г. Рязань, ул. Почтовая, 20, 100", price))
            verify(exactly = 0) { newBuildings.finalPriceInfo() }
            assertEquals("Final price new building with rising: ${price + 100000u}", newBuildings.finalPriceInfo())
            verify(exactly = 1) { newBuildings.finalPriceInfo() }
            verify(exactly = 0) { newBuildings.propertyInfo() }
        }
    }

    @Test
    fun `вычисление цены со скидкой для вторички (финальная цена больше максимальной)`() {
        val listPrice = listOf(UInt.MAX_VALUE - 99999u, UInt.MAX_VALUE)
        for (price in listPrice) {
            newBuildings = spyk(NewBuildings("г. Рязань, ул. Почтовая, 20, 100", price))
            assertEquals(listOf("Final price new building with rising: ${UInt.MAX_VALUE}",
                "The final price exceeds the maximum price",
                "The maximum possible price is set").joinToString("\n"),
                newBuildings.finalPriceInfo())
        }
    }

    @Test
    fun `тестируем propertyInfo-вызовов 1`() {
        newBuildings = spyk(NewBuildings("г. Рязань, ул. Почтовая, 20, 100", 1500000u))
        assertEquals(listOf(
            "Address: г. Рязань, ул. Почтовая, 20, 100",
            "Price: 1500000 ",
            "Info about property").joinToString("\n"), newBuildings.propertyInfo())
        verify(exactly = 1) { newBuildings.propertyInfo() }
        verify(exactly = 0) { newBuildings.finalPriceInfo() }
    }

}