package test

import homework.lesson1.NewBuildings
import io.mockk.clearAllMocks
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

const val minValue: Long = 0
const val value50k: Long = 50000
const val value50001: Long = 50001
const val value70k: Long = 70000
const val value100k: Long = 100000
const val value150k: Long = 150000
const val maxValue: Long = 4294967295
const val maxValueMinus50k: Long = 4294917295
const val maxValueMinus100k: Long = 4294867295
const val maxValueMinus99999: Long = 4294867296

class NewBuildingsTest {


    private lateinit var newBuildings: NewBuildings

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun discountInfo() {
        val trueResult = "No sale for new building"
        newBuildings = spyk(NewBuildings("г. Рязань, ул. Почтовая, 20, 100", 1000000u))
        val resultDiscountInfo = newBuildings.discountInfo()
        verify(exactly = 1) { newBuildings.discountInfo() }
        assertEquals(trueResult, resultDiscountInfo)
    }


    @ParameterizedTest
    @ValueSource(longs = [minValue, value50k])
    fun `вычисление цены для новостройки (финальная цена меньше или равна минимальной)`(price: Long) {
        val priceNewBuildings = price.toUInt()
        val trueResult = listOf("Final price new building with rising: $value150k",
            "Final price is less than or equal to the minimum price",
            "The minimum possible price is set").joinToString("\n")
        newBuildings = spyk(NewBuildings("г. Рязань, ул. Почтовая, 20, 100", priceNewBuildings))
        assertEquals(trueResult, newBuildings.finalPriceInfo())
    }

    @ParameterizedTest
    @ValueSource(longs = [value50001, maxValueMinus100k])
    fun `вычисление цены для новостройки (финальная цена больше минимальной и меньше или равна максимальной)`(price: Long) {
        val priceNewBuildings = price.toUInt()
        val trueResult = "Final price new building with rising: ${priceNewBuildings + 100000u}"
        newBuildings = spyk(NewBuildings("г. Рязань, ул. Почтовая, 20, 100", priceNewBuildings))
        verify(exactly = 0) { newBuildings.finalPriceInfo() }
        assertEquals(trueResult, newBuildings.finalPriceInfo())
        verify(exactly = 1) { newBuildings.finalPriceInfo() }
        verify(exactly = 0) { newBuildings.propertyInfo() }
    }


    @ParameterizedTest
    @ValueSource(longs = [maxValueMinus99999, maxValue])
    fun `вычисление цены со скидкой для вторички (финальная цена больше максимальной)`(price: Long) {
        val priceNewBuildings = price.toUInt()
        val trueResult = listOf("Final price new building with rising: $maxValue",
            "The final price exceeds the maximum price",
            "The maximum possible price is set").joinToString("\n")
        newBuildings = spyk(NewBuildings("г. Рязань, ул. Почтовая, 20, 100", priceNewBuildings))
        assertEquals(trueResult, newBuildings.finalPriceInfo())
    }


    @Test
    fun propertyInfo() {
        val trueResult = listOf(
            "Address: г. Рязань, ул. Почтовая, 20, 100",
            "Price: 1500000 ",
            "Info about property").joinToString("\n")
        newBuildings = spyk(NewBuildings("г. Рязань, ул. Почтовая, 20, 100", 1500000u))
        assertEquals(trueResult, newBuildings.propertyInfo())
        verify(exactly = 1) { newBuildings.propertyInfo() }
        verify(exactly = 0) { newBuildings.finalPriceInfo() }
    }

}