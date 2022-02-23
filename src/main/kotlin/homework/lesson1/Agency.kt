package homework.lesson1

class Agency(private val second: SecondBuilding, private val new: NewBuilding) {

    var currentCodeBaseAgency = 1u
        private set

    private val textAddInBaseAgency = "added to base building"

    fun baseAgencyInfo(): String {

        val listBuilding = listOf(new, second)
        var returnString = ""
        listBuilding.forEach {
            returnString += it.propertyInfo()
            returnString += "$textAddInBaseAgency with index: $currentCodeBaseAgency \n"
            currentCodeBaseAgency++
        }
        return returnString
    }
}