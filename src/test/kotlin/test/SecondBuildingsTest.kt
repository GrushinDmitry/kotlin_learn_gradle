package test

import homework.lesson1.SecondBuildings
import io.mockk.clearAllMocks
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(MockKExtension::class)
class SecondBuildingsTest {

    @SpyK
    var secondBuildings = SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 750000u)

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `тестируем secondBuilding с помощью Spy-изменение цены однократно, вызовов 3 и 4`() {
        secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 700000u))
        secondBuildings.discountInfo()
        secondBuildings.discountInfo()
        secondBuildings.discountInfo()
        verify(exactly = 3) { secondBuildings.discountInfo() }
        verify(exactly = 0) { secondBuildings.propertyInfo() }
        assertEquals("Price with sale: 650000", secondBuildings.discountInfo())
        verify(exactly = 4) { secondBuildings.discountInfo() }
    }


    @Test
    fun `тестируем trendPriceInfo secondBuilding с помощью Spy`() {
        secondBuildings.trendPriceInfo(2021u)
        secondBuildings.trendPriceInfo(2021u)
        verify(exactly = 2) { secondBuildings.trendPriceInfo(2021u) }
        verify(exactly = 0) { secondBuildings.discountInfo() }
        verify(exactly = 0) { secondBuildings.propertyInfo() }
        assertEquals("Resale second building will fall price in 2022 \n",
            secondBuildings.trendPriceInfo(2022u))
        verify(exactly = 1) { secondBuildings.trendPriceInfo(2022u) }
    }

    @ParameterizedTest
    @CsvSource("50000", "70000", "100000")
    fun `вычисление цены со скидкой для вторички (финальная цена меньше или равна скидке)`(
        price: UInt,
    ) {
        secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", price))
        assertEquals(listOf("Price with sale: 50000",
            "Final price is less than or equal to the minimum price",
            "The minimum possible price is set").joinToString("\n"),
            secondBuildings.discountInfo())
    }

    @Test
    fun `вычисление цены со скидкой для вторички (цена равна 0)`() {
        secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", UInt.MIN_VALUE))
        assertEquals(listOf("Price with sale: 50000",
            "The discount does not apply, because the price cannot be less than the discount",
            "The minimum possible price is set").joinToString("\n"),
            secondBuildings.discountInfo())

    }

    @Test
    fun `вычисление цены со скидкой для вторички (цена максимальная - 4,294,967,295`() {
        secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", UInt.MAX_VALUE))
        assertEquals("Price with sale: 4294917295", secondBuildings.discountInfo())
    }

    @Test
    fun `тестируем propertyInfo`() {
        secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 1000000u))
        assertEquals(listOf(
            "Address: г. Рязань, ул. Ленина, 8, 34",
            "Price: 1000000 ",
            "Info about property").joinToString("\n"), secondBuildings.propertyInfo())
        verify(exactly = 1) { secondBuildings.propertyInfo() }
    }
}