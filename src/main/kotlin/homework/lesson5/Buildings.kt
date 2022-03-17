package homework.lesson5

class Buildings(
    val address: String,
    val type: TypeBuildings,
    val area: Int,
    val yearConstruction: Int,
    val price: Int
) {
    fun checkInputData() {
        when {
            address == "" -> error("Building address not entered")
            area < 1 -> error("Area cannot be negative or zero")
            yearConstruction < 1780 -> error("Year of construction cannot be younger than 1780")
            price < 1 -> error("Price cannot be negative or zero")
        }
    }

}