package homework.lesson1

class NewBuildings(address: String, price: UInt) : Property(address, price) {

    private val risingPrice: UInt = 100000u

    private val differenceMaxPriceRisingPrice: UInt = UInt.MAX_VALUE - risingPrice

    private val textFinalPrice = "Final price new building with rising:"

    override fun discountInfo() = "No sale for new building"

    fun finalPriceInfo() = if (differenceMaxPriceRisingPrice >= price) {
        listOf("$textFinalPrice ${price + risingPrice}").joinToString("/n")
    } else {
        listOf("$textFinalPrice ${UInt.MAX_VALUE}",
            "The final price exceeds the maximum price",
            "The maximum possible price is set").joinToString("/n")
    }

}