package homework.lesson5


class Service(private val rate: Int, listBuildings: List<Buildings>) {

    private val sequenceBuildings = listBuildings.asSequence()

    /*Метод, который преобразует поля нашего класса в описание на английском,
    а цену преобразует в валюту (курс, можно создать константой) и возвращает
     новую коллекцию отсортированную по возрастанию цены.*/
    fun doDescriptionConvertSort() = sequenceBuildings.sortedBy { it.price }
        .map {
            "ADDRESS: ${it.address}"
            "TYPE: ${it.type}"
            "AREA: ${it.area}"
            "YEAR CONSTRUCTION: ${it.yearConstruction}"
            "PRICE IN $: ${(it.price.toDouble() / rate)}"
        }


    /*Метод, группирующий элементы списка по типу кузова или по какому-либо заранее выбранному полю*/
    fun groupingByType() = sequenceBuildings.groupBy { it.type }

    /*Метод, возвращающий количество элементов из списка соответствующих переданному условию,
    после чего преобразовать список названий и взять первые 3 элемента*/
    fun returnFirstThreeArea(threshold: Int) = sequenceBuildings.filter { it.area > threshold }.take(3)


}