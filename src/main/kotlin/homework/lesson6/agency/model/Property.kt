package homework.lesson6.agency.model

data class Property(
    val address: String,
    val area: Int
)

data class PropertyBaseItem(
    val address: String,
    val area: Int,
    val price: Int
) {
    constructor(property: Property, price: Int) : this(property.address, property.area, price)
}
