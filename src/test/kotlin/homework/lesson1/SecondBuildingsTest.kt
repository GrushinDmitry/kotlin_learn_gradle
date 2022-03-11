package homework.lesson1

import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MockKExtension::class)
class SecondBuildingsTest {


    @Test
    fun `вычисление цены со скидкой для вторички (вызовов 3 и 4)`() {
        val expectedResult = "Price with sale: 650000"
        val secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 700000u))


        secondBuildings.discountInfo()
        secondBuildings.discountInfo()
        secondBuildings.discountInfo()


        verify(exactly = 3) { secondBuildings.discountInfo() }
        verify(exactly = 0) { secondBuildings.propertyInfo() }
        assertEquals(expectedResult, secondBuildings.discountInfo())
    }


    @Test
    fun trendPriceInfo() {
        val expectedResult = "Resale second building will fall price in 2022 \n"
        val secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Княжья , 20", 800000u))


        secondBuildings.trendPriceInfo(2021u)
        secondBuildings.trendPriceInfo(2021u)


        verify(exactly = 2) { secondBuildings.trendPriceInfo(2021u) }
        verify(exactly = 0) { secondBuildings.discountInfo() }
        verify(exactly = 0) { secondBuildings.propertyInfo() }
        assertEquals(expectedResult, secondBuildings.trendPriceInfo(2022u))

    }

    @ParameterizedTest
    @ValueSource(longs = [value50k, value70k, value100k])
    fun `вычисление цены со скидкой для вторички (финальная цена меньше или равна скидке)`(price: Long) {
        val priceSecondBuildings = price.toUInt()
        val expectedResult = listOf("Price with sale: $value50k",
            "Final price is less than or equal to the minimum price",
            "The minimum possible price is set").joinToString("\n")
        val secondBuildings = SecondBuildings("г. Рязань, ул. Победы, 8, 34", priceSecondBuildings)


        assertEquals(expectedResult, secondBuildings.discountInfo())
    }

    @Test
    fun `вычисление цены со скидкой для вторички (цена равна 0)`() {
        val expectedResult = listOf("Price with sale: $value50k",
            "The discount does not apply, because the price cannot be less than the discount",
            "The minimum possible price is set").joinToString("\n")
        val secondBuildings = SecondBuildings("г. Рязань, ул. Первомайская, 8, 34", UInt.MIN_VALUE)


        assertEquals(expectedResult, secondBuildings.discountInfo())

    }

    @Test
    fun `вычисление цены со скидкой для вторички (цена максимальная - 4,294,967,295`() {
        val expectedResult = "Price with sale: $maxValueMinus50k"
        val secondBuildings = SecondBuildings("г. Новгород, ул. Ленина, 100, 150", UInt.MAX_VALUE)


        assertEquals(expectedResult, secondBuildings.discountInfo())
    }

    @Test
    fun propertyInfo() {
        val expectedResult = listOf("Address: г. Москва, ул. Победы, 3, 1",
            "Price: 1000000 ",
            "Info about property").joinToString("\n")
        val secondBuildings = SecondBuildings("г. Москва, ул. Победы, 3, 1", 1000000u)


        assertEquals(expectedResult, secondBuildings.propertyInfo())
    }
}

private const val value50k: Long = 50000
private const val value70k: Long = 70000
private const val value100k: Long = 100000
private const val maxValueMinus50k: Long = 4294917295