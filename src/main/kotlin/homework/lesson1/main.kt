package homework.lesson1

fun main() {
    val secondBuild = SecondBuilding("г. Рязань, ул. Ленина, 8, 34", 7000000u)

    val newBuild = NewBuilding("г. Рязань, ул. Почтовая, 20, 100", 10000000u)

    val build = listOf(secondBuild, newBuild)

    build.forEach {
        it.propertyInfo()
        it.discountInfo()
        println()
    }

    secondBuild.trendPriceInfo(2022u)
    println(newBuild.textFinalPrice)
    val agency = Agency(secondBuild, newBuild)
    agency.addInDataBase()
}

