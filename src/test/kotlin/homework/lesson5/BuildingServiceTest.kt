package homework.lesson5

import homework.lesson5.Language.EN
import homework.lesson5.Language.RU
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BuildingServiceTest {


    @Test
    fun doDescriptionConvertSortEN() {
        val actual = BuildingService().toLocalizedDescriptionSorted(buldings, rates.en, EN)

        val expected = sequenceOf(
            """\n${textDescriptionAddress.en} ${buildingTula.address.en}
            |${textDescriptionType.en} ${buildingTula.type.en}
            |${textDescriptionPrice.en} ${buildingTula.price / rates.en}""".trimMargin(),
            """\n${textDescriptionAddress.en} ${buildingChelyabinsk.address.en}
            |${textDescriptionType.en} ${buildingChelyabinsk.type.en}
            |${textDescriptionPrice.en} ${buildingChelyabinsk.price / rates.en}""".trimMargin(),
            """\n${textDescriptionAddress.en} ${buildingSmolensk.address.en}
            |${textDescriptionType.en} ${buildingSmolensk.type.en}
            |${textDescriptionPrice.en} ${buildingSmolensk.price / rates.en}""".trimMargin(),
            """\n${textDescriptionAddress.en} ${buildingArkhangelsk.address.en}
            |${textDescriptionType.en} ${buildingArkhangelsk.type.en}
            |${textDescriptionPrice.en} ${buildingArkhangelsk.price / rates.en}""".trimMargin(),
            """\n${textDescriptionAddress.en} ${buildingLeningrad.address.en}
            |${textDescriptionType.en} ${buildingLeningrad.type.en}
            |${textDescriptionPrice.en} ${buildingLeningrad.price / rates.en}""".trimMargin()
        ).toList()

        assertEquals(expected, actual)
    }


    @Test
    fun doDescriptionConvertSortRU() {
        val actual = BuildingService().toLocalizedDescriptionSorted(buldings, rates.ru, RU)

        val expected = sequenceOf(
            """\n${textDescriptionAddress.ru} ${buildingTula.address.ru}
            |${textDescriptionType.ru} ${buildingTula.type.ru}
            |${textDescriptionPrice.ru} ${buildingTula.price / rates.ru}""".trimMargin(),
            """\n${textDescriptionAddress.ru} ${buildingChelyabinsk.address.ru}
            |${textDescriptionType.ru} ${buildingChelyabinsk.type.ru}
            |${textDescriptionPrice.ru} ${buildingChelyabinsk.price / rates.ru}""".trimMargin(),
            """\n${textDescriptionAddress.ru} ${buildingSmolensk.address.ru}
            |${textDescriptionType.ru} ${buildingSmolensk.type.ru}
            |${textDescriptionPrice.ru} ${buildingSmolensk.price / rates.ru}""".trimMargin(),
            """\n${textDescriptionAddress.ru} ${buildingArkhangelsk.address.ru}
            |${textDescriptionType.ru} ${buildingArkhangelsk.type.ru}
            |${textDescriptionPrice.ru} ${buildingArkhangelsk.price / rates.ru}""".trimMargin(),
            """\n${textDescriptionAddress.ru} ${buildingLeningrad.address.ru}
            |${textDescriptionType.ru} ${buildingLeningrad.type.ru}
            |${textDescriptionPrice.ru} ${buildingLeningrad.price / rates.ru}""".trimMargin()
        ).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun groupingByType() {
        val actual = BuildingService().groupingByType(buldings)

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

        val actual = BuildingService().returnFirstThreeArea(buldings, thresholdArea)

        val expected = listOf(
            "${textDescriptionAddress.en} ${addressChelyabinsk.en}",
            "${textDescriptionAddress.en} ${addressSmolensk.en}",
            "${textDescriptionAddress.en} ${addressLeningrad.en}",
        )
        assertEquals(expected, actual)
    }

    val rates = Rates(1, 100)
    val typePublic = Localized("Общественные", "Public")
    val typeAgriculturial = Localized("Сельскохозяйственные", "Agriculturial")
    val typeResidential = Localized("Жилые", "Residential")
    val typeIndustrial = Localized("Индустриальные", "Industrial")
    val textDescriptionAddress = Localized("Адрес строения:", "Building address:")
    val textDescriptionType = Localized("Тип строения:", "Building type:")
    val textDescriptionPrice = Localized("Цена строения в рублях:", "Building price in \$:")
    val addressTula = Localized(
        "Тульская обл., г. Ступино, въезд Космонавтов, 97", "Tula region, Stupino city, Cosmonauts entrance, 97"
    )
    val addressChelyabinsk = Localized(
        "Челябинская область, город Истра, Гоголевский бульвар, 96",
        "Chelyabinsk region, the city of Istra, Gogol Boulevard, 96"
    )
    val addressSmolensk = Localized(
        "Смоленская область, г. Зарайск, пер. Ленина, 64", "Smolensk region, Zaraysk city, lane Lenin, 64"
    )
    val addressArkhangelsk = Localized(
        "Архангельская область, город Шатура, пер. Чехова, 53", "Arkhangelsk region, Shatura city, lane. Chekhov, 53"
    )
    val addressLeningrad = Localized(
        "Ленинградская область, город Дорохово, наб. Гагарина, 18",
        "Leningrad region, Dorokhovo city, nab. Gagarina, 18"
    )

    val buildingTula = Building(
        addressTula, typePublic, 32, 1980, 1000000
    )
    val buildingChelyabinsk = Building(
        addressChelyabinsk, typeResidential, 55, 1999, 1000000
    )
    val buildingSmolensk = Building(
        addressSmolensk, typeAgriculturial, 90, 2013, 5000000
    )
    val buildingArkhangelsk = Building(
        addressArkhangelsk, typeIndustrial, 47, 2022, 8000000
    )
    val buildingLeningrad = Building(
        addressLeningrad, typeAgriculturial, 1000, 1910, 110000000
    )
    val buldings = listOf(
        buildingTula, buildingChelyabinsk, buildingSmolensk, buildingArkhangelsk, buildingLeningrad
    )
}

