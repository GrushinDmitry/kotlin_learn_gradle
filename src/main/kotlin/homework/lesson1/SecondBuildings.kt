package homework.lesson1

class SecondBuildings(address: String, price: UInt) : Property(address, price) {

    private val priceSale: UInt = 50000u

    private val textTrendPrice = "Resale second building will fall price"

    private val textSalePrice = "Price with sale:"


    override fun discountInfo() = if (price < priceSale) {
        listOf("$textSalePrice $priceSale",
            "The discount does not apply, because the price cannot be less than the discount",
            "The minimum possible price is set").joinToString("\n")

    } else if ((price - priceSale) <= priceSale) {
        listOf("$textSalePrice $priceSale",
            "Final price is less than or equal to the minimum price",
            "The minimum possible price is set").joinToString("\n")
    } else {
        "$textSalePrice ${price - priceSale}"
    }


    fun trendPriceInfo(year: UInt) = "$textTrendPrice in $year \n"

}
