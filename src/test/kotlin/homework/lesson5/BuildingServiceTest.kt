package homework.lesson5

import homework.lesson5.Language.EN
import homework.lesson5.Language.RU
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BuildingServiceTest {


    @Test
    fun doDescriptionConvertSortEN() {
        val actual = BuildingService().toLocalizedDescriptionSorted(buldings, rates, EN)

        val expected = listOf(
            """Building address: Tula region, Stupino city, Cosmonauts entrance, 97
            |Building type: Public
            |Building price in ${'$'}: 10000""".trimMargin(),
            """Building address: Chelyabinsk region, the city of Istra, Gogol Boulevard, 96
            |Building type: Residential
            |Building price in ${'$'}: 10000""".trimMargin(),
            """Building address: Smolensk region, Zaraysk city, lane Lenin, 64
            |Building type: Agriculturial
            |Building price in ${'$'}: 50000""".trimMargin(),
            """Building address: Arkhangelsk region, Shatura city, lane. Chekhov, 53
            |Building type: Industrial
            |Building price in ${'$'}: 80000""".trimMargin(),
            """Building address: Leningrad region, Dorokhovo city, nab. Gagarina, 18
            |Building type: Agriculturial
            |Building price in ${'$'}: 1100000""".trimMargin()
        )
        assertEquals(expected, actual)
    }


    @Test
    fun doDescriptionConvertSortRU() {
        val actual = BuildingService().toLocalizedDescriptionSorted(buldings, rates, RU)

        val expected = listOf(
            """Адрес строения: Тульская обл., г. Ступино, въезд Космонавтов, 97
            |Тип строения: Общественные
            |Цена строения в рублях: 1000000""".trimMargin(),
            """Адрес строения: Челябинская область, город Истра, Гоголевский бульвар, 96
            |Тип строения: Жилые
            |Цена строения в рублях: 1000000""".trimMargin(),
            """Адрес строения: Смоленская область, г. Зарайск, пер. Ленина, 64
            |Тип строения: Сельскохозяйственные
            |Цена строения в рублях: 5000000""".trimMargin(),
            """Адрес строения: Архангельская область, город Шатура, пер. Чехова, 53
            |Тип строения: Индустриальные
            |Цена строения в рублях: 8000000""".trimMargin(),
            """Адрес строения: Ленинградская область, город Дорохово, наб. Гагарина, 18
            |Тип строения: Сельскохозяйственные
            |Цена строения в рублях: 110000000""".trimMargin()
        )
        assertEquals(expected, actual)
    }

    @Test
    fun groupingByType() {
        val actual = BuildingService().groupingByType(buldings)

        val expected = mapOf(
            "Общественные" to listOf(
                Building(
                    Localized(
                        "Тульская обл., г. Ступино, въезд Космонавтов, 97",
                        "Tula region, Stupino city, Cosmonauts entrance, 97"
                    ),
                    Localized("Общественные", "Public"), 32, 1980, 1000000
                )
            ),
            "Жилые" to listOf(
                Building(
                    Localized(
                        "Челябинская область, город Истра, Гоголевский бульвар, 96",
                        "Chelyabinsk region, the city of Istra, Gogol Boulevard, 96"
                    ), Localized("Жилые", "Residential"), 55, 1999, 1000000
                )
            ),
            "Сельскохозяйственные" to listOf(
                Building(
                    Localized(
                        "Смоленская область, г. Зарайск, пер. Ленина, 64",
                        "Smolensk region, Zaraysk city, lane Lenin, 64"
                    ), Localized("Сельскохозяйственные", "Agriculturial"),
                    90, 2013, 5000000
                ), Building(
                    Localized(
                        "Ленинградская область, город Дорохово, наб. Гагарина, 18",
                        "Leningrad region, Dorokhovo city, nab. Gagarina, 18"
                    ), Localized("Сельскохозяйственные", "Agriculturial"),
                    1000, 1910, 110000000
                )
            ),
            "Индустриальные" to listOf(
                Building(
                    Localized(
                        "Архангельская область, город Шатура, пер. Чехова, 53",
                        "Arkhangelsk region, Shatura city, lane. Chekhov, 53"
                    ), Localized("Индустриальные", "Industrial"),
                    47, 2022, 8000000
                )
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun returnFirstThreeArea() {
        val thresholdArea = 50

        val actual = BuildingService().returnFirstThreeArea(buldings, thresholdArea)

        val expected = listOf(
            "Building address: Chelyabinsk region, the city of Istra, Gogol Boulevard, 96",
            "Building address: Smolensk region, Zaraysk city, lane Lenin, 64",
            "Building address: Leningrad region, Dorokhovo city, nab. Gagarina, 18",
        )
        assertEquals(expected, actual)
    }

    val rates = Rates(1, 100)

    val buldings = listOf(
        Building(
            Localized(
                "Тульская обл., г. Ступино, въезд Космонавтов, 97",
                "Tula region, Stupino city, Cosmonauts entrance, 97"
            ),
            Localized("Общественные", "Public"), 32, 1980, 1000000
        ),
        Building(
            Localized(
                "Челябинская область, город Истра, Гоголевский бульвар, 96",
                "Chelyabinsk region, the city of Istra, Gogol Boulevard, 96"
            ), Localized("Жилые", "Residential"), 55, 1999, 1000000
        ),
        Building(
            Localized(
                "Смоленская область, г. Зарайск, пер. Ленина, 64",
                "Smolensk region, Zaraysk city, lane Lenin, 64"
            ), Localized("Сельскохозяйственные", "Agriculturial"), 90, 2013, 5000000
        ),
        Building(
            Localized(
                "Архангельская область, город Шатура, пер. Чехова, 53",
                "Arkhangelsk region, Shatura city, lane. Chekhov, 53"
            ), Localized("Индустриальные", "Industrial"), 47, 2022, 8000000
        ),
        Building(
            Localized(
                "Ленинградская область, город Дорохово, наб. Гагарина, 18",
                "Leningrad region, Dorokhovo city, nab. Gagarina, 18"
            ), Localized("Сельскохозяйственные", "Agriculturial"), 1000, 1910, 110000000
        )
    )


}

