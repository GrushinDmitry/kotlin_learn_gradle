package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property
import org.springframework.stereotype.Repository

@Repository
class PropertyBase {
    private val properties = mutableMapOf<Int, Property>()

    fun saveProperty(property: Property) {
        properties[property.id] = property
    }

    fun delPropertyById(id: Int) = properties.remove(id)


    fun findProperty(priceLess: Int, pageNum: Int, pageSize: Int): List<Property> =
        properties.values.asSequence()
            .filter { it.price < priceLess }
            .sortedBy { it.price }
            .drop(pageSize * (pageNum - 1))
            .take(pageSize)
            .toList()

    fun getProperty(id: Int): Property? = properties[id]
}