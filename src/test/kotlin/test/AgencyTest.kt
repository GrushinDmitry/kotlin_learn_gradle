package test

import homework.lesson1.Agency
import homework.lesson1.NewBuildings
import homework.lesson1.SecondBuildings
import io.mockk.clearAllMocks
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class AgencyTest {


    var secondBuildings = spyk(SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 750000u))
    var newBuildings = spyk(NewBuildings("г. Рязань, ул. Почтовая, 20, 100", 1000000u))
    var agency = spyk(Agency(secondBuildings, newBuildings))

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `тестируем baseAgencyInfo`() {
        Assertions.assertEquals(listOf(
            "Address: г. Рязань, ул. Почтовая, 20, 100",
            "Price: 1000000 ",
            "Info about property",
            "Added to base buildings with index: 1",
            "Address: г. Рязань, ул. Ленина, 8, 34",
            "Price: 750000 ",
            "Info about property",
            "Added to base buildings with index: 2").joinToString("\n"), agency.baseAgencyInfo())
        verify(exactly = 1) { agency.baseAgencyInfo() }
    }
}