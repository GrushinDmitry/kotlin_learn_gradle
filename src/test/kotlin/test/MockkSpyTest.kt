package test

import homework.lesson1.NewBuildings
import homework.lesson1.SecondBuildings
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class MockkSpyTest {
    @MockK(relaxUnitFun = true)
    lateinit var newBuildings: NewBuildings

    @SpyK
    var secondBuildings = SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 750000u)

    @Test
    fun `тестируем textFinalPrice newBuilding с помощью Mockk`() {
        every { newBuildings.textFinalPrice } returns "final price new building: 10100000"
        //newBuildings.textFinalPrice = "final price new building: 10200000"
        assertEquals("final price new building: 10100000", newBuildings.textFinalPrice)
    }

    @Test
    fun `тестируем textFinalPrice newBuilding с помощью Mockk-пустая строка`() {
        every { newBuildings.textFinalPrice } returns ""
        //newBuildings.textFinalPrice = "final price new building: 10200000"
        assertEquals("", newBuildings.textFinalPrice)
    }

    @Test
    fun `тестируем discountInfo() newBuilding с помощью Mockk`() {
        every { newBuildings.discountInfo() } returns "Скидок больше не будет :( very bad"
        assertEquals("Скидок больше не будет :( very bad", newBuildings.discountInfo())
    }

    @Test
    fun `тестируем discountInfo() newBuilding с помощью Mockk-пустая строка`() {
        every { newBuildings.discountInfo() } returns ""
        assertEquals("", newBuildings.discountInfo())
    }

    @Test
    fun `тестируем secondBuilding с помощью Spy-изменение цены`() {
        secondBuildings.discountInfo()
        secondBuildings.discountInfo()
        secondBuildings = SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 700000u)
        secondBuildings.discountInfo()
        secondBuildings.discountInfo()
        secondBuildings.discountInfo()
        assertEquals("price with sale: 650000 ", secondBuildings.discountInfo())
    }

    @Test
    fun `тестируем secondBuilding с помощью Spy-количество вызовов 3`() {
        secondBuildings.discountInfo()
        secondBuildings.discountInfo()
        secondBuildings.discountInfo()
        verify(exactly = 3) { secondBuildings.discountInfo() }
        assertEquals("price with sale: 700000 ", secondBuildings.discountInfo())
    }

    @Test
    fun `тестируем secondBuilding с помощью Spy-количество вызовов 0 и 1`() {
        verify(exactly = 0) { secondBuildings.discountInfo() }
        assertEquals("price with sale: 700000 ", secondBuildings.discountInfo())
        verify(exactly = 1) { secondBuildings.discountInfo() }
    }

    @Test
    fun `тестируем secondBuilding с помощью Spy-изменение цены до 5000`() {
        secondBuildings = SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 5000u)
        assertEquals("""price with sale: 50000 
            |Скидка не применена, т.к. цена не может быть меньше скидки
            |Выше указана новая цена""".trimMargin(), secondBuildings.discountInfo())
    }

    @Test
    fun `тестируем trendPriceInfo secondBuilding с помощью Spy`() {
        secondBuildings.trendPriceInfo(2021u)
        secondBuildings.trendPriceInfo(2021u)
        verify(exactly = 2) { secondBuildings.trendPriceInfo(2021u) }
        assertEquals("resale second building will fall price in 2022 \n",
            secondBuildings.trendPriceInfo(2022u))
        verify(exactly = 1) { secondBuildings.trendPriceInfo(2022u) }
    }
}