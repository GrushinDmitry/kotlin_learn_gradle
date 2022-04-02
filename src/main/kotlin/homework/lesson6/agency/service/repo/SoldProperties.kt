package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property
import org.springframework.stereotype.Repository

@Repository
class SoldProperties {
    private val soldProperties = mutableMapOf<Int, Property>()

    fun add(property: Property): Property {
        soldProperties[property.id] = property
        return property
    }

    fun delById(id: Int): Property? = soldProperties.remove(id)

    fun find(priceMax: Int, pageNum: Int, pageSize: Int): List<Property> =
        soldProperties.values.asSequence()
            .filter { it.price < priceMax }
            .sortedBy { it.price }
            .drop(pageSize * (pageNum - 1))
            .take(pageSize)
            .toList()

    fun get(id: Int): Property? = soldProperties[id]
}