package homework.lesson5

import org.junit.jupiter.api.Test

internal class ServiceTest {
    val rate = 100
    val range = 0..4
    val listAddress = listOf(
        "Tula region, Stupino city, Cosmonauts entrance, 97",
        "Leningrad region, Dorokhovo city, nab. Gagarina, 18",
        "Chelyabinsk region, the city of Istra, Gogol Boulevard, 96",
        "Arkhangelsk region, Shatura city, lane. Chekhov, 53",
        "Smolensk region, Zaraysk city, lane Lenin, 64"
    )
    val listTypeBuildings = listOf(
        TypeBuildings.PUBLIC,
        TypeBuildings.AGRICULTURIAL,
        TypeBuildings.RESIDENTIAL,
        TypeBuildings.INDUSTRIAL,
        TypeBuildings.AGRICULTURIAL
    )
    val listArea = listOf(32, 1000, 55, 47, 90)
    val listYearConstruction = listOf(1980, 1910, 1999, 2022, 2013)
    val listPrice = listOf(1000000, 110000000, 1000000, 8000000, 5000000)
    val listBuildings = range.map {
        Buildings(
            listAddress[it],
            listTypeBuildings[it],
            listArea[it],
            listYearConstruction[it],
            listPrice[it]
        )
    }

    val serviceBuildings = Service(rate, listBuildings)

    @Test
    fun doDescriptionConvertSort() {
    }

    @Test
    fun groupingByType() {
    }

    @Test
    fun returnFirstThree() {
    }
}

