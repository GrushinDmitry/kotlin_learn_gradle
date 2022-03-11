package test

import homework.lesson1.SecondBuildings
import io.mockk.clearAllMocks
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MockKExtension::class)
class SecondBuildingsTest {


    private lateinit var secondBuildings: SecondBuildings

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `вычисление цены со скидкой для вторички (вызовов 3 и 4)`() {
        val trueResult = "Price with sale: 650000"
        secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 700000u))
        secondBuildings.discountInfo()
        secondBuildings.discountInfo()
        secondBuildings.discountInfo()
        verify(exactly = 3) { secondBuildings.discountInfo() }
        verify(exactly = 0) { secondBuildings.propertyInfo() }
        assertEquals(trueResult, secondBuildings.discountInfo())
        verify(exactly = 4) { secondBuildings.discountInfo() }
    }


    @Test
    fun trendPriceInfo() {
        val trueResult = "Resale second building will fall price in 2022 \n"
        secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 800000u))
        secondBuildings.trendPriceInfo(2021u)
        secondBuildings.trendPriceInfo(2021u)
        verify(exactly = 2) { secondBuildings.trendPriceInfo(2021u) }
        verify(exactly = 0) { secondBuildings.discountInfo() }
        verify(exactly = 0) { secondBuildings.propertyInfo() }
        assertEquals(trueResult, secondBuildings.trendPriceInfo(2022u))
        verify(exactly = 1) { secondBuildings.trendPriceInfo(2022u) }
    }

    @ParameterizedTest
    @ValueSource(longs = [value50k, value70k, value100k])
    fun `вычисление цены со скидкой для вторички (финальная цена меньше или равна скидке)`(price: Long) {
        val priceSecondBuildings = price.toUInt()
        val trueResult = listOf("Price with sale: $value50k",
            "Final price is less than or equal to the minimum price",
            "The minimum possible price is set").joinToString("\n")
        secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", priceSecondBuildings))
        assertEquals(trueResult, secondBuildings.discountInfo())
    }

    @Test
    fun `вычисление цены со скидкой для вторички (цена равна 0)`() {
        val trueResult = listOf("Price with sale: $value50k",
            "The discount does not apply, because the price cannot be less than the discount",
            "The minimum possible price is set").joinToString("\n")
        secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", UInt.MIN_VALUE))
        assertEquals(trueResult, secondBuildings.discountInfo())

    }

    @Test
    fun `вычисление цены со скидкой для вторички (цена максимальная - 4,294,967,295`() {
        val trueResult = "Price with sale: $maxValueMinus50k"
        secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", UInt.MAX_VALUE))
        assertEquals(trueResult, secondBuildings.discountInfo())
    }

    @Test
    fun propertyInfo() {
        val trueResult = listOf("Address: г. Рязань, ул. Ленина, 8, 34",
            "Price: 1000000 ",
            "Info about property").joinToString("\n")
        secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 1000000u))
        assertEquals(trueResult, secondBuildings.propertyInfo())
        verify(exactly = 1) { secondBuildings.propertyInfo() }
    }
}