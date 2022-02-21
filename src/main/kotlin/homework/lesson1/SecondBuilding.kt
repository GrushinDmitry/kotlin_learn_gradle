package homework.lesson1

open class SecondBuilding(final override val address: String, final override val price: UInt) : Property() {

    private val priceSale: UInt = 50000u
    private val textTrendPrice = "resale second building will fall price"
    override fun discountInfo() {
        println("price with sale: ${calculateFinalPrice(price, priceSale)} ")

    }

    private fun calculateFinalPrice(price: UInt, priceSale: UInt) = price - priceSale
    fun trendPriceInfo(year: UInt) = println("$textTrendPrice in $year \n")

}

