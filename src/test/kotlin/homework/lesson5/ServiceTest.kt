package homework.lesson5

import homework.lesson5.Language.EN
import homework.lesson5.Language.RU
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ServiceTest {
    val BuildingsTulaRU = Buildings(
        "Тульская обл., г. Ступино, въезд Космонавтов, 97",
        "Общественные",
        32,
        1980,
        1000000
    )
    val BuildingsChelyabinskRU = Buildings(
        "Челябинская область, город Истра, Гоголевский бульвар, 96",
        "Жилые",
        55,
        1999,
        1000000
    )
    val BuildingsSmolenskRU = Buildings(
        "Смоленская область, г. Зарайск, пер. Ленина, 64",
        "Сельскохозяйственные",
        90,
        2013,
        5000000
    )
    val BuildingsArkhangelskRU = Buildings(
        "Архангельская область, город Шатура, пер. Чехова, 53",
        "Индустриальные",
        47,
        2022,
        8000000
    )
    val BuildingsLeningradRU = Buildings(
        "Ленинградская область, город Дорохово, наб. Гагарина, 18",
        "Сельскохозяйственные",
        1000,
        1910,
        110000000
    )

    @Test
    fun doDescriptionConvertSortEN() {
        val rate = 100

        val actual = ServiceBuildings.doLocalizationConvertSort(rate, EN)

        val expected = listOf(
            Buildings(
                "Tula region, Stupino city, Cosmonauts entrance, 97",
                "Public",
                32,
                1980,
                1000000 / rate
            ),
            Buildings(
                "Chelyabinsk region, the city of Istra, Gogol Boulevard, 96",
                "Residential",
                55,
                1999,
                1000000 / rate
            ),
            Buildings(
                "Smolensk region, Zaraysk city, lane Lenin, 64",
                "Agriculturial",
                90,
                2013,
                5000000 / rate
            ),
            Buildings(
                "Arkhangelsk region, Shatura city, lane. Chekhov, 53",
                "Industrial",
                47,
                2022,
                8000000 / rate
            ),
            Buildings(
                "Leningrad region, Dorokhovo city, nab. Gagarina, 18",
                "Agriculturial",
                1000,
                1910,
                110000000 / rate
            ),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun doDescriptionConvertSortRU() {
        val rate = 1

        val actual = ServiceBuildings.doLocalizationConvertSort(rate, RU)

        val expected = listOf(
            BuildingsTulaRU,
            BuildingsChelyabinskRU,
            BuildingsSmolenskRU,
            BuildingsArkhangelskRU,
            BuildingsLeningradRU
        )
        assertEquals(expected, actual)
    }

    @Test
    fun groupingByType() {
        val actual = ServiceBuildings.groupingByType()

        val expected = listOf(
            "Общественные" to listOf(BuildingsTulaRU),
            "Сельскохозяйственные" to listOf(BuildingsLeningradRU, BuildingsSmolenskRU),
            "Жилые" to listOf(BuildingsChelyabinskRU),
            "Индустриальные" to listOf(BuildingsArkhangelskRU)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun returnFirstThreeArea() {
        val thresholdArea = 50

        val actual = ServiceBuildings.returnFirstThreeArea(thresholdArea)

        val expected = listOf(
            "Address buildings: Ленинградская область, город Дорохово, наб. Гагарина, 18",
            "Address buildings: Челябинская область, город Истра, Гоголевский бульвар, 96",
            "Address buildings: Смоленская область, г. Зарайск, пер. Ленина, 64"
        )
        assertEquals(expected, actual)
    }
}

