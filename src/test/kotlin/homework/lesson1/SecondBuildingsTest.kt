package homework.lesson1

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MockKExtension::class)
class SecondBuildingsTest {

    val secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 700000u))

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `вычисление цены со скидкой для вторички (вызовов 3 и 4)`() {
        every { secondBuildings.price } returns 700000u


        val actualResult = secondBuildings.discountInfo()


        val expectedResult = "Price with sale: 650000"
        assertEquals(expectedResult, actualResult)
    }


    @Test
    fun trendPriceInfo() {
        val actualResult = secondBuildings.trendPriceInfo(2022u)

        val expectedResult = "Resale second building will fall price in 2022 \n"
        assertEquals(expectedResult, actualResult)
    }

    @ParameterizedTest
    @ValueSource(longs = [value50k, value70k, value100k])
    fun `вычисление цены со скидкой для вторички (финальная цена меньше или равна скидке)`(price: Long) {
        every { secondBuildings.price } returns price.toUInt()


        val actualResult = secondBuildings.discountInfo()


        val expectedResult = listOf(
            "Price with sale: $value50k",
            "Final price is less than or equal to the minimum price",
            "The minimum possible price is set"
        ).joinToString("\n")
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `вычисление цены со скидкой для вторички (цена равна 0)`() {
        every { secondBuildings.price } returns UInt.MIN_VALUE


        val actualResult = secondBuildings.discountInfo()


        val expectedResult = listOf(
            "Price with sale: $value50k",
            "The discount does not apply, because the price cannot be less than the discount",
            "The minimum possible price is set"
        ).joinToString("\n")
        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `вычисление цены со скидкой для вторички (цена максимальная - 4,294,967,295`() {
        every { secondBuildings.price } returns UInt.MAX_VALUE


        val actualResult = secondBuildings.discountInfo()


        val expectedResult = "Price with sale: $maxValueMinus50k"
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun propertyInfo() {
        every { secondBuildings.price } returns 1000000u


        val actualResult = secondBuildings.propertyInfo()


        val expectedResult = listOf(
            "Address: г. Рязань, ул. Ленина, 8, 34",
            "Price: 1000000 ",
            "Info about property"
        ).joinToString("\n")
        assertEquals(expectedResult, actualResult)
    }
}

private const val value50k: Long = 50000
private const val value70k: Long = 70000
private const val value100k: Long = 100000
private const val maxValueMinus50k: Long = 4294917295