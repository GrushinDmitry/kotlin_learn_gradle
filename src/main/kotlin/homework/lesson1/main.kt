package homework.lesson1

fun main() {
    val secondBuildings = SecondBuildings("г. Рязань, ул. Ленина, 8, 34", 7000000u)

    val newBuildings = NewBuildings("г. Рязань, ул. Почтовая, 20, 100", 10000000u)

    val buildings = listOf(secondBuildings, newBuildings)

    val agency = Agency(secondBuildings, newBuildings)

    buildings.forEach {
        println(it.propertyInfo())
        println(it.discountInfo())
        println()
    }

    println(secondBuildings.trendPriceInfo(2022u))

    println(newBuildings.textFinalPrice)

    println(agency.baseAgencyInfo())
}