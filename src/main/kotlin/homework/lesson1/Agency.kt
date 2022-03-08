package homework.lesson1

class Agency(private val second: SecondBuildings, private val new: NewBuildings) {

    var currentIndexBaseAgency = 0u
        private set

    private val textAddInBaseAgency = "Added to base buildings"

    fun baseAgencyInfo() = listOf(new, second)
           .joinToString("\n")
        {
            listOf(it.propertyInfo(), "$textAddInBaseAgency with index: ${++currentIndexBaseAgency}").joinToString("\n")
        }

}
