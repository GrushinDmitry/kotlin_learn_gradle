package homework.lesson5

import homework.lesson5.Language.EN
import homework.lesson5.Language.RU
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BuildingServiceTest {


    @Test
    fun doDescriptionConvertSortEN() {
        val rate = 100

        val actual =
            BuildingService(stringServiceAddress, stringServiceType).toLocalizedDescriptionSort(listBuldings, rate, EN)

        val expected = listOf(
            listOf(listAddress[0][EN.name], listTypeBuildings[0][EN.name], "10000"),
            listOf(listAddress[2][EN.name], listTypeBuildings[2][EN.name], "10000"),
            listOf(listAddress[4][EN.name], listTypeBuildings[1][EN.name], "50000"),
            listOf(listAddress[3][EN.name], listTypeBuildings[3][EN.name], "80000"),
            listOf(listAddress[1][EN.name], listTypeBuildings[1][EN.name], "1100000")
        )
        assertEquals(expected, actual)

    }


    @Test
    fun doDescriptionConvertSortRU() {
        val rate = 1

        val actual =
            BuildingService(stringServiceAddress, stringServiceType).toLocalizedDescriptionSort(listBuldings, rate, RU)

        val expected = listOf(
            listOf(listAddress[0][RU.name], listTypeBuildings[0][RU.name], "1000000"),
            listOf(listAddress[2][RU.name], listTypeBuildings[2][RU.name], "1000000"),
            listOf(listAddress[4][RU.name], listTypeBuildings[1][RU.name], "5000000"),
            listOf(listAddress[3][RU.name], listTypeBuildings[3][RU.name], "8000000"),
            listOf(listAddress[1][RU.name], listTypeBuildings[1][RU.name], "110000000")
        )
        assertEquals(expected, actual)
    }

    @Test
    fun groupingByType() {
        val actual = BuildingService(stringServiceAddress, stringServiceType).groupingByType(listBuldings)

        val expected = mapOf(
            "Общественные" to listOf(buildingTula),
            "Жилые" to listOf(buildingChelyabinsk),
            "Сельскохозяйственные" to listOf(buildingSmolensk, buildingLeningrad),
            "Индустриальные" to listOf(buildingArkhangelsk)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun returnFirstThreeArea() {
        val thresholdArea = 50

        val actual =
            BuildingService(stringServiceAddress, stringServiceType).returnFirstThreeArea(listBuldings, thresholdArea)

        val expected = listOf(
            "Address buildings: Челябинская область, город Истра, Гоголевский бульвар, 96",
            "Address buildings: Смоленская область, г. Зарайск, пер. Ленина, 64",
            "Address buildings: Ленинградская область, город Дорохово, наб. Гагарина, 18",
        )
        assertEquals(expected, actual)
    }

    val listAddress = listOf(
        mapOf(
            "EN" to "Tula region, Stupino city, Cosmonauts entrance, 97",
            "RU" to "Тульская обл., г. Ступино, въезд Космонавтов, 97"
        ),
        mapOf(
            "EN" to "Leningrad region, Dorokhovo city, nab. Gagarina, 18",
            "RU" to "Ленинградская область, город Дорохово, наб. Гагарина, 18"
        ),
        mapOf(
            "EN" to "Chelyabinsk region, the city of Istra, Gogol Boulevard, 96",
            "RU" to "Челябинская область, город Истра, Гоголевский бульвар, 96"
        ),
        mapOf(
            "EN" to "Arkhangelsk region, Shatura city, lane. Chekhov, 53",
            "RU" to "Архангельская область, город Шатура, пер. Чехова, 53"
        ),
        mapOf(
            "EN" to "Smolensk region, Zaraysk city, lane Lenin, 64",
            "RU" to "Смоленская область, г. Зарайск, пер. Ленина, 64"
        ),
    )
    val stringServiceAddress = StringService(listAddress)
    val listTypeBuildings = listOf(
        mapOf(
            "EN" to "Public", "RU" to "Общественные"
        ),
        mapOf(
            "EN" to "Agriculturial", "RU" to "Сельскохозяйственные"
        ),
        mapOf(
            "EN" to "Residential", "RU" to "Жилые"
        ),
        mapOf(
            "EN" to "Industrial", "RU" to "Индустриальные"
        ),

        )
    val stringServiceType = StringService(listTypeBuildings)
    val buildingTula = Building(
        0, 0, 32, 1980, 1000000
    )
    val buildingChelyabinsk = Building(
        2, 2, 55, 1999, 1000000
    )
    val buildingSmolensk = Building(
        4, 1, 90, 2013, 5000000
    )
    val buildingArkhangelsk = Building(
        3, 3, 47, 2022, 8000000
    )
    val buildingLeningrad = Building(
        1, 1, 1000, 1910, 110000000
    )
    val listBuldings = listOf(
        buildingTula, buildingChelyabinsk, buildingSmolensk, buildingArkhangelsk, buildingLeningrad
    )
}

