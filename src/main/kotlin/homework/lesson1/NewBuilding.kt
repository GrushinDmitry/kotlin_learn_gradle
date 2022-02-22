package homework.lesson1

open class NewBuilding(final override val address: String, final override val price: UInt) : Property() {

    override fun discountInfo() {
        println("no sale for new building")

    }


    private val risingPrice: UInt = 100000u
    val textFinalPrice = "final price new building: ${calculateFinalPrice(price, risingPrice)} \n"
    private fun calculateFinalPrice(price: UInt, risingPrice: UInt) = price + risingPrice


}