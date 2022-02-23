package homework.lesson1

abstract class Property(val address: String, val price: UInt) {

    abstract fun discountInfo(): String

    open fun propertyInfo() = listOf("address: $address", "price: $price ", "info about property")
        .joinToString("\n")

}