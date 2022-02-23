package homework.lesson1

class NewBuildings(address: String, price: UInt) : Property(address, price) {

    private val risingPrice: UInt = 100000u

    val textFinalPrice = "final price new building: ${calculateFinalPrice(price, risingPrice)} \n"

    override fun discountInfo() = "no sale for new building"

    private fun calculateFinalPrice(price: UInt, risingPrice: UInt) = price + risingPrice

}