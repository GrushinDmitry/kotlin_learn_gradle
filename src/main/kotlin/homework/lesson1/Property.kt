package homework.lesson1

abstract class Property(val address: String, val price: UInt) {

    abstract fun discountInfo(): String

    open fun propertyInfo() = listOf("Address: $address", "Price: $price ", "Info about property")
        .joinToString("\n")

}