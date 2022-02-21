package homework.lesson1

abstract class Property {


    abstract val address: String

    abstract val price: UInt

    open fun propertyInfo() = "info about property \n ".print()

    private fun String.print() {

        println("address: $address")
        println("price: $price")
        println(this)


    }

    abstract fun discountInfo()
}
