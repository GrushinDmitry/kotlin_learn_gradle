package homework.lesson1

class SecondBuildings(address: String, price: UInt) : Property(address, price) {

    private val priceSale: UInt = 50000u

    private val textTrendPrice = "resale second building will fall price"

    override fun discountInfo() = "price with sale: ${calculateFinalPrice(price, priceSale)} "

    private fun calculateFinalPrice(price: UInt, priceSale: UInt) = price - priceSale

    fun trendPriceInfo(year: UInt) = "$textTrendPrice in $year \n"

}