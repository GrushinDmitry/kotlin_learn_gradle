package homework.lesson1

fun main() {
    val secondBuild = SecondBuilding("г. Рязань, ул. Ленина, 8, 34", 7000000u)

    val newBuild = NewBuilding("г. Рязань, ул. Почтовая, 20, 100", 10000000u)

    val build = listOf(secondBuild, newBuild)

    val agency = Agency(secondBuild, newBuild)

    build.forEach {
        println(it.propertyInfo())
        println(it.discountInfo())
        println()
    }

    println(secondBuild.trendPriceInfo(2022u))

    println(newBuild.textFinalPrice)

    println(agency.baseAgencyInfo())
}