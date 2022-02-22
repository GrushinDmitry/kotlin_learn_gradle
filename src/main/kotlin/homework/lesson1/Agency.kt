package homework.lesson1

class Agency(secondProp: SecondBuilding, newProp: NewBuilding) {
    var codeBase = 1u
    private val textAdd = "added to base building"
    private val second = secondProp
    private val new = newProp

    fun addInDataBase() {
        val proper = listOf<Property>(new, second)
        proper.forEach() {
            it.propertyInfo()
            println("$textAdd with code: $codeBase \n")
            codeBase++
        }
    }


}