package homework.lesson1

abstract class Property(val address: String, val price: UInt) {

    abstract fun discountInfo(): String

    open fun propertyInfo() = "address: $address \n" +
            "price: $price \ninfo about property \n"

}