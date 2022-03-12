package homework.lesson1

import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class NewBuildingsTest {


    @Test
    fun discountInfo() {
        val expectedResult = "No sale for new building"
        val newBuildings = spyk(NewBuildings("г. Липецк, ул. Почтовая, 20, 100", 1000000u))
        val actualResult = newBuildings.discountInfo()
        verify(exactly = 1) { newBuildings.discountInfo() }


        assertEquals(expectedResult, actualResult)
    }


    @ParameterizedTest
    @ValueSource(longs = [minValue, value50k])
    fun `вычисление цены для новостройки (финальная цена меньше или равна минимальной)`(price: Long) {
        val priceNewBuildings = price.toUInt()
        val expectedResult = listOf(
            "Final price new building with rising: $value150k",
            "Final price is less than or equal to the minimum price",
            "The minimum possible price is set"
        ).joinToString("\n")
        val newBuildings = NewBuildings("г. Рязань, ул. Лесная, 20, 100", priceNewBuildings)


        assertEquals(expectedResult, newBuildings.finalPriceInfo())
    }

    @ParameterizedTest
    @ValueSource(longs = [value50001, maxValueMinus100k])
    fun `вычисление цены для новостройки (финальная цена больше минимальной и меньше или равна максимальной)`(price: Long) {
        val priceNewBuildings = price.toUInt()
        val expectedResult = "Final price new building with rising: ${priceNewBuildings + 100000u}"
        val newBuildings = NewBuildings("г. Рыбное, ул. Почтовая, 20, 100", priceNewBuildings)


        assertEquals(expectedResult, newBuildings.finalPriceInfo())
    }


    @ParameterizedTest
    @ValueSource(longs = [maxValueMinus99999, maxValue])
    fun `вычисление цены со скидкой для вторички (финальная цена больше максимальной)`(price: Long) {
        val priceNewBuildings = price.toUInt()
        val expectedResult = listOf(
            "Final price new building with rising: $maxValue",
            "The final price exceeds the maximum price",
            "The maximum possible price is set"
        ).joinToString("\n")
        val newBuildings = NewBuildings("", priceNewBuildings)


        assertEquals(expectedResult, newBuildings.finalPriceInfo())
    }


    @Test
    fun propertyInfo() {
        val expectedResult = listOf(
            "Address: г. Рязань, ул. Почтовая, 20, 100",
            "Price: 1500000 ",
            "Info about property"
        ).joinToString("\n")
        val newBuildings = NewBuildings("г. Рязань, ул. Почтовая, 20, 100", 1500000u)


        assertEquals(expectedResult, newBuildings.propertyInfo())

    }

}

private const val minValue: Long = 0
private const val value50k: Long = 50000
private const val value50001: Long = 50001
private const val value150k: Long = 150000
private const val maxValue: Long = 4294967295
private const val maxValueMinus100k: Long = 4294867295
private const val maxValueMinus99999: Long = 4294867296