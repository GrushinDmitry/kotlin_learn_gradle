package homework.lesson6.agency.service

import homework.lesson6.agency.model.Property
import homework.lesson6.agency.service.client.BaseClient
import homework.lesson6.agency.service.repo.PropertyBase
import org.springframework.stereotype.Service

@Service
class Agency(private val baseClient: BaseClient, private val propertyBase: PropertyBase) {


    fun findProperty(priceLess: Int, pageNum: Int, pageSize: Int): List<Property> {
        require(priceLess > 0) { "The price cannot be negative" }
        return propertyBase.findProperty(priceLess, pageNum, pageSize)
            .ifEmpty { throw IllegalArgumentException("The properties with price less $priceLess not found") }
    }

    fun saveProperty(id: Int) {
        val property = baseClient.getProperty(id) ?: noProperty(id)
        propertyBase.saveProperty(property)
    }

    fun delPropertyById(id: Int) = propertyBase.delPropertyById(id) ?: noProperty(id)

    fun getProperty(id: Int): Property = propertyBase.getProperty(id) ?: noProperty(id)

    private fun noProperty(id: Int): Nothing = throw IllegalArgumentException("The property with id: $id not found")

}

