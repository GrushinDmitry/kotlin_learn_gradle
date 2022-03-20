package homework.lesson5

import homework.lesson5.Language.RU

class BuildingService(private val stringServiceAddress: StringService, private val stringServiceType: StringService) {


    /*Метод, который преобразует поля нашего класса в описание на английском,
 а цену преобразует в валюту (курс, можно создать константой) и возвращает
  новую коллекцию отсортированную по возрастанию цены.*/
    fun toLocalizedDescriptionSort(
        listBuildings: List<Building>,
        rate: Int,
        localized: Language
    ): List<List<String>> =
        listBuildings.asSequence().sortedBy { it.price }
            .map {
                listOf(
                    stringServiceAddress.returnLocalized(it.address, localized),
                    stringServiceType.returnLocalized(it.type, localized),
                    (it.price / rate).toString()
                )
            }.toList()


    /*Метод, группирующий элементы списка по типу кузова или по какому-либо заранее выбранному полю*/
    fun groupingByType(listBuildings: List<Building>): Map<String, List<Building>> =
        listBuildings.groupBy { stringServiceType.returnLocalized(it.type, RU) }

    /*Метод, возвращающий количество элементов из списка соответствующих переданному условию,
    после чего преобразовать список названий и взять первые 3 элемента*/
    fun returnFirstThreeArea(listBuildings: List<Building>, threshold: Int): List<String> = listBuildings.asSequence()
        .filter { it.area > threshold }
        .map { "Address buildings: ${stringServiceAddress.returnLocalized(it.address, RU)}" }
        .take(3).toList()


}


