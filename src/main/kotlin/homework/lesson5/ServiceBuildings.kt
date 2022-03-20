package homework.lesson5

import homework.lesson5.Language.RU


object ServiceBuildings {
    private val range = 0..4
    private val listAddress = listOf(
        listOf(
            "Tula region, Stupino city, Cosmonauts entrance, 97",
            "Тульская обл., г. Ступино, въезд Космонавтов, 97"
        ),
        listOf(
            "Leningrad region, Dorokhovo city, nab. Gagarina, 18",
            "Ленинградская область, город Дорохово, наб. Гагарина, 18"
        ),
        listOf(
            "Chelyabinsk region, the city of Istra, Gogol Boulevard, 96",
            "Челябинская область, город Истра, Гоголевский бульвар, 96"
        ),
        listOf(
            "Arkhangelsk region, Shatura city, lane. Chekhov, 53",
            "Архангельская область, город Шатура, пер. Чехова, 53"
        ),
        listOf(
            "Smolensk region, Zaraysk city, lane Lenin, 64",
            "Смоленская область, г. Зарайск, пер. Ленина, 64"
        ),
    )
    private val listTypeBuildings =
        listOf(
            listOf(
                "Public",
                "Общественные"
            ),
            listOf(
                "Agriculturial",
                "Сельскохозяйственные"
            ),
            listOf(
                "Residential",
                "Жилые"
            ),
            listOf(
                "Industrial",
                "Индустриальные"
            ),
            listOf(
                "Agriculturial",
                "Сельскохозяйственные"
            ),
        )

    private val listArea = listOf(32, 1000, 55, 47, 90)
    private val listYearConstruction = listOf(1980, 1910, 1999, 2022, 2013)
    private val listPrice = listOf(1000000, 110000000, 1000000, 8000000, 5000000)
    private val listBuildingsRu =
        range.map {
            Buildings(
                listAddress[it][RU.value],
                listTypeBuildings[it][RU.value],
                listArea[it],
                listYearConstruction[it],
                listPrice[it]
            )
        }


    /*Метод, который преобразует поля нашего класса в описание на английском,
 а цену преобразует в валюту (курс, можно создать константой) и возвращает
  новую коллекцию отсортированную по возрастанию цены.*/
    fun doLocalizationConvertSort(rate: Int, localization: Language) =
        listBuildingsRu.asSequence().sortedBy { it.price }
            .map {
                Buildings(
                    listAddress[returnIndexList(listAddress, it.address)][localization.value],
                    listTypeBuildings[returnIndexList(listTypeBuildings, it.type)][localization.value],
                    it.area,
                    it.yearConstruction,
                    it.price / rate
                )

            }.toList()

    private fun returnIndexList(list: List<List<String>>, element: String): Int =
        list.indexOf(list.asSequence().filter { it[RU.value] == element }.take(1).flatMap { it }.toList())


    /*Метод, группирующий элементы списка по типу кузова или по какому-либо заранее выбранному полю*/
    fun groupingByType() = listBuildingsRu.groupBy { it.type }.toList()

    /*Метод, возвращающий количество элементов из списка соответствующих переданному условию,
    после чего преобразовать список названий и взять первые 3 элемента*/
    fun returnFirstThreeArea(threshold: Int) = listBuildingsRu.asSequence()
        .filter { it.area > threshold }
        .map { "Address buildings: ${it.address}" }
        .take(3).toList()


}


