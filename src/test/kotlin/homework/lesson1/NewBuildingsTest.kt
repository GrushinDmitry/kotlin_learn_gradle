package homework.lesson1

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class NewBuildingsTest {

    val newBuildings = spyk(NewBuildings("г. Липецк, ул. Почтовая, 20, 100", 1000000u))

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }


    @Test
    fun discountInfo() {
        val actualResult = newBuildings.discountInfo()


        val expectedResult = "No sale for new building"
        assertEquals(expectedResult, actualResult)
    }


    @ParameterizedTest
    @ValueSource(longs = [minValue, value50k])
    fun `вычисление цены для новостройки (финальная цена меньше или равна минимальной)`(price: Long) {
        every { newBuildings.price } returns price.toUInt()


        val actualResult = newBuildings.finalPriceInfo()


        val expectedResult = listOf(
            "Final price new building with rising: $value150k",
            "Final price is less than or equal to the minimum price",
            "The minimum possible price is set"
        ).joinToString("\n")
        assertEquals(expectedResult, actualResult)
    }

    @ParameterizedTest
    @ValueSource(longs = [value50001, maxValueMinus100k])
    fun `вычисление цены для новостройки (финальная цена больше минимальной и меньше или равна максимальной)`(price: Long) {
        every { newBuildings.price } returns price.toUInt()


        val actualResult = newBuildings.finalPriceInfo()


        val expectedResult = "Final price new building with rising: ${price.toUInt() + 100000u}"
        assertEquals(expectedResult, actualResult)
    }


    @ParameterizedTest
    @ValueSource(longs = [maxValueMinus99999, maxValue])
    fun `вычисление цены со скидкой для вторички (финальная цена больше максимальной)`(price: Long) {
        every { newBuildings.price } returns price.toUInt()


        val actualResult = newBuildings.finalPriceInfo()


        val expectedResult = listOf(
            "Final price new building with rising: $maxValue",
            "The final price exceeds the maximum price",
            "The maximum possible price is set"
        ).joinToString("\n")
        assertEquals(expectedResult, actualResult)
    }


    @Test
    fun propertyInfo() {
        every { newBuildings.price } returns 1500000u


        val actualResult = newBuildings.propertyInfo()


        val expectedResult = listOf(
            "Address: г. Липецк, ул. Почтовая, 20, 100",
            "Price: 1500000 ",
            "Info about property"
        ).joinToString("\n")
        assertEquals(expectedResult, actualResult)

    }

}

private const val minValue: Long = 0
private const val value50k: Long = 50000
private const val value50001: Long = 50001
private const val value150k: Long = 150000
private const val maxValue: Long = 4294967295
private const val maxValueMinus100k: Long = 4294867295
private const val maxValueMinus99999: Long = 4294867296