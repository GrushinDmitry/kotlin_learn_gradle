package homework.lesson6.agency.service

import homework.lesson6.agency.model.Property
import homework.lesson6.agency.service.client.PropertiesClient
import homework.lesson6.agency.service.repo.SoldProperties
import org.springframework.stereotype.Service

@Service
class Agency(private val baseClient: PropertiesClient, private val soldProperties: SoldProperties) {


    fun findSoldPropertiesByMaxPrice(maxPrice: Int, pageNum: Int, pageSize: Int): List<Property> {
        require(maxPrice > 0 && pageNum > 0 && pageSize > 0) { "The arguments cannot be negative" }
        return soldProperties.find(maxPrice, pageNum, pageSize)
    }

    fun addSoldProperty(id: Int): Property {
        val property = baseClient.getProperty(id) ?: noProperty(id)
        soldProperties.add(property)
        return property
    }

    fun delSoldPropertyById(id: Int) = soldProperties.delById(id) ?: noProperty(id)

    fun getSoldProperty(id: Int): Property = soldProperties.get(id) ?: noProperty(id)

    private fun noProperty(id: Int): Nothing = throw IllegalArgumentException("The property with id: $id not found")

}

