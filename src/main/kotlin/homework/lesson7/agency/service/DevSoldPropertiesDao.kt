package homework.lesson7.agency.service

import homework.lesson7.agency.model.AddSoldPropertyRequest
import homework.lesson7.agency.model.Property
import homework.lesson7.agency.service.client.PropertiesClient
import homework.lesson7.agency.service.repo.SoldPropertiesDao
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service


@Service
@Profile("dev", "test")
class DevSoldPropertiesDao(
    private val propertiesClient: PropertiesClient,
    private val soldPropertiesDao: SoldPropertiesDao
) {


    fun findSoldPropertiesByMaxPrice(maxPrice: Int, pageNum: Int, pageSize: Int): List<Property> {
        require(maxPrice > 0 && pageNum > 0 && pageSize > 0) { "The arguments must be positive" }
        return soldPropertiesDao.find(maxPrice, pageNum, pageSize)
    }

    fun addSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest): Property {
        val property = propertiesClient.getProperty(addSoldPropertyRequest.id)
            ?: propertyNotFound(addSoldPropertyRequest.id)
        soldPropertiesDao.add(property)
        return property
    }

    fun deleteSoldPropertyById(id: Int): Property = soldPropertiesDao.deleteById(id) ?: propertyNotFound(id)

    fun getSoldProperty(id: Int): Property = soldPropertiesDao.get(id) ?: propertyNotFound(id)

    private fun propertyNotFound(id: Int): Nothing =
        throw IllegalArgumentException("The property with id: $id not found")

}

