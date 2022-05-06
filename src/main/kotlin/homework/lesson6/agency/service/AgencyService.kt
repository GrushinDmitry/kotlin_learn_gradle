package homework.lesson6.agency.service

import homework.lesson6.agency.model.AddPropertyAndGetIdResponse
import homework.lesson6.agency.model.AddSoldPropertyRequest
import homework.lesson6.agency.model.Property
import homework.lesson6.agency.model.Status
import homework.lesson6.agency.service.client.PropertiesClient
import homework.lesson6.agency.service.repo.SoldPropertiesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Service
class AgencyService(
    private val propertiesClient: PropertiesClient,
    private val soldPropertiesDao: SoldPropertiesDao
) {
    private val requestNumber = AtomicInteger(0)
    private val requestNumberToPropertyId = ConcurrentHashMap<Int, AddPropertyAndGetIdResponse>()

    fun findSoldPropertiesByMaxPrice(maxPrice: Int, pageNum: Int, pageSize: Int): List<Property> {
        require(maxPrice > 0 && pageNum > 0 && pageSize > 0) { "The arguments must be positive" }
        return soldPropertiesDao.find(maxPrice, pageNum, pageSize)
    }

    fun addSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest): AddPropertyAndGetIdResponse {
        val fixedRequestNumber = requestNumber.incrementAndGet()
        requestNumberToPropertyId[fixedRequestNumber] = AddPropertyAndGetIdResponse(fixedRequestNumber)
        getAndSaveProperty(addSoldPropertyRequest, fixedRequestNumber)
        return requestNumberToPropertyId.getValue(fixedRequestNumber)
    }

    fun deleteSoldPropertyById(id: Int): Property = soldPropertiesDao.deleteById(id) ?: propertyNotFound(id)

    fun getSoldProperty(id: Int): Property = soldPropertiesDao.get(id) ?: propertyNotFound(id)

    private fun propertyNotFound(id: Int): Nothing =
        throw IllegalArgumentException("The property with id: $id not found")

    fun getIdByRequestNumber(number: Int): AddPropertyAndGetIdResponse =
        requestNumberToPropertyId[number] ?: throw IllegalArgumentException("The request for number: $number not found")

    private fun getAndSaveProperty(addSoldPropertyRequest: AddSoldPropertyRequest, fixedRequestNumber: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            val property = propertiesClient.getProperty(addSoldPropertyRequest.id)
            if (property == null) {
                requestNumberToPropertyId[fixedRequestNumber] =
                    AddPropertyAndGetIdResponse(fixedRequestNumber, Status.ERROR)
            } else withContext(Dispatchers.IO) {
                val soldProperty = soldPropertiesDao.add(property)
                saveNotNullPropertyRequest(fixedRequestNumber, soldProperty)
            }
        }
    }

    private fun saveNotNullPropertyRequest(fixedRequestNumber: Int, soldProperty: Property) {
        requestNumberToPropertyId[fixedRequestNumber] = AddPropertyAndGetIdResponse(
            fixedRequestNumber, Status.DONE, soldPropertiesDao.add(soldProperty).id
        )
    }
}