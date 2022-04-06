package homework.lesson6.agency.service

import homework.lesson6.agency.model.AddSoldPropertyRequest
import homework.lesson6.agency.model.Property
import homework.lesson6.agency.service.client.PropertiesClient
import homework.lesson6.agency.service.repo.SoldPropertiesRepository
import org.springframework.stereotype.Service

@Service
class AgencyService(
    private val propertiesClient: PropertiesClient,
    private val soldPropertiesRepository: SoldPropertiesRepository
) {


    fun findSoldPropertiesByMaxPrice(maxPrice: Int, pageNum: Int, pageSize: Int): List<Property> {
        require(maxPrice > 0 && pageNum > 0 && pageSize > 0) { "The arguments must be positive" }
        return soldPropertiesRepository.find(maxPrice, pageNum, pageSize)
    }

    fun addSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest): Property {
        val property = propertiesClient.getProperty(addSoldPropertyRequest.id)
            ?: propertyNotFound(addSoldPropertyRequest.id)
        soldPropertiesRepository.add(property)
        return property
    }

    fun deleteSoldPropertyById(id: Int): Property = soldPropertiesRepository.deleteById(id) ?: propertyNotFound(id)

    fun getSoldProperty(id: Int): Property = soldPropertiesRepository.get(id) ?: propertyNotFound(id)

    private fun propertyNotFound(id: Int): Nothing =
        throw IllegalArgumentException("The property with id: $id not found")

}

