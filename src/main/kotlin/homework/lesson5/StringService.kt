package homework.lesson5

class StringService(private val list: List<Map<String, String>>) {
    fun returnLocalized(ref: Int, language: Language): String =
        list[ref][language.name] ?: throw NoSuchElementException()

}