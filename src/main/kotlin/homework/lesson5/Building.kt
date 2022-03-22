package homework.lesson5

data class Building(
    val address: Localized,
    val type: Localized,
    val area: Int,
    val yearOfConstruction: Int,
    val price: Int
)