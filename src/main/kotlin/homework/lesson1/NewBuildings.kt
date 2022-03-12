package homework.lesson1

class NewBuildings(address: String, price: UInt) : Property(address, price) {

    private val risingPrice: UInt = 100000u

    private val minPrice: UInt = 150000u

    private val maxPrice: UInt = UInt.MAX_VALUE

    private val differenceMaxPriceRisingPrice: UInt = maxPrice - risingPrice

    private val textFinalPrice = "Final price new building with rising:"

    override fun discountInfo() = "No sale for new building"

    fun finalPriceInfo() = if ((differenceMaxPriceRisingPrice >= price) && ((price+risingPrice) > minPrice)) {
        listOf("$textFinalPrice ${price + risingPrice}").joinToString("\n")
    } else if (price <= minPrice) {
        listOf("$textFinalPrice $minPrice",
            "Final price is less than or equal to the minimum price",
            "The minimum possible price is set").joinToString("\n")
    } else {
        listOf("$textFinalPrice $maxPrice",
            "The final price exceeds the maximum price",
            "The maximum possible price is set").joinToString("\n")
    }

}