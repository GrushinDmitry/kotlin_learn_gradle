package homework.lesson5


class BuildingService() {


    /*Метод, который преобразует поля нашего класса в описание на английском,
 а цену преобразует в валюту (курс, можно создать константой) и возвращает
  новую коллекцию отсортированную по возрастанию цены.*/
    fun toLocalizedDescriptionSorted(
        Buildings: List<Building>, rate: Rates, language: Language
    ): List<String> = Buildings.asSequence().sortedBy { it.price }.map {
        it.localizedDescriptionBuilding(language, rate)
    }.toList()



    /*Метод, группирующий элементы списка по типу кузова или по какому-либо заранее выбранному полю*/
    fun groupingByType(Buildings: List<Building>): Map<String, List<Building>> = Buildings.groupBy { it.type.ru }

        /*Метод, возвращающий количество элементов из списка соответствующих переданному условию,
        после чего преобразовать список названий и взять первые 3 элемента*/
        fun returnFirstThreeArea(Buildings: List< Building >
        , threshold: Int
        )
        : List<String> =
        Buildings.asSequence().filter { it.area > threshold }.map { "Building address: ${it.address.en}" }.take(3)
            .toList()


}


