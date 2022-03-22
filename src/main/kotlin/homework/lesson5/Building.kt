package homework.lesson5

import homework.lesson5.Language.EN
import homework.lesson5.Language.RU

data class Building(
    val address: Localized,
    val type: Localized,
    val area: Int,
    val yearOfConstruction: Int,
    val price: Int
) {
    fun localizedDescriptionBuilding(language: Language, rate: Rates) = when (language) {
        EN -> """Building address: ${address.en}
            |Building type: ${type.en}
            |Building price in $: ${price / rate.en}""".trimMargin()
        RU -> """Адрес строения: ${address.ru}
            |Тип строения: ${type.ru}
            |Цена строения в рублях: ${price / rate.ru}""".trimMargin()
    }
}