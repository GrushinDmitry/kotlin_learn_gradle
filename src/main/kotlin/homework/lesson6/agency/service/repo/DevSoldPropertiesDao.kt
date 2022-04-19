package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("dev", "test")
class DevSoldPropertiesDao : SoldPropertiesDao {

    private val soldProperties = mutableMapOf<Int, Property>()
    private var counter = 0

    override fun add(property: Property): Property {
        val newProperty = Property(++counter, property.address, property.area, property.price)
        soldProperties[counter] = newProperty
        return newProperty
    }

    override fun deleteById(id: Int): Property? = soldProperties.remove(id)

    override fun find(priceMax: Int, pageNum: Int, pageSize: Int): List<Property> =
        soldProperties.values.asSequence()
            .filter { it.price < priceMax }
            .sortedBy { it.price }
            .drop(pageSize * (pageNum - 1))
            .take(pageSize)
            .toList()

    override fun get(id: Int): Property? = soldProperties[id]

    override fun getId(): Int = counter
}