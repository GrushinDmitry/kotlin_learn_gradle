package homework.lesson1


import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class AgencyTest {


    val secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 750000u))
    val newBuildings = spyk(NewBuildings("г. Рязань, ул. Почтовая, 20, 100", 1000000u))


    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun baseAgencyInfo() {
        every { newBuildings.price } returns 200000u
        val agency = Agency(secondBuildings, newBuildings)
        val expectedResult = listOf(
            "Address: г. Рязань, ул. Почтовая, 20, 100",
            "Price: 200000 ",
            "Info about property",
            "Added to base buildings with index: 1",
            "Address: г. Рязань, ул. Ленина, 8, 34",
            "Price: 750000 ",
            "Info about property",
            "Added to base buildings with index: 2"
        ).joinToString("\n")


        assertEquals(expectedResult, agency.baseAgencyInfo())
        verify(exactly = 1) { newBuildings.propertyInfo() }

    }
}

