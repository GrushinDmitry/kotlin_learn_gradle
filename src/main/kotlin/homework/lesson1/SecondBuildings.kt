package homework.lesson1

class SecondBuildings(address: String, price: UInt) : Property(address, price) {

    private val priceSale: UInt = 50000u

    private val textTrendPrice = "resale second building will fall price"

    override fun discountInfo() = if (calculateFinalPrice(price, priceSale) != priceSale) {
        "price with sale: ${calculateFinalPrice(price, priceSale)} "
    } else {
        """price with sale: ${calculateFinalPrice(price, priceSale)} 
            |Скидка не применена, т.к. цена не может быть меньше скидки
            |Выше указана новая цена""".trimMargin()
    }


    private fun calculateFinalPrice(price: UInt, priceSale: UInt): UInt = if (priceSale >= price) {
        priceSale
    } else {
        price - priceSale
    }


    fun trendPriceInfo(year: UInt) = "$textTrendPrice in $year \n"

}
