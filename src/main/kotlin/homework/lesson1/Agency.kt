package homework.lesson1

class Agency(private val second: SecondBuildings, private val new: NewBuildings) {

    var currentIndexBaseAgency = 0u
        private set

    private val textAddInBaseAgency = "added to base buildings"

    fun baseAgencyInfo() = listOf(new, second)
           .joinToString("\n")
        {
            "${it.propertyInfo()} $textAddInBaseAgency with index: ${++currentIndexBaseAgency}"
        }

}
