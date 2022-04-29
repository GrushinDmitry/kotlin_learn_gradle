package homework.lesson6.agency.service

import homework.lesson6.agency.model.AddSoldPropertyRequest
import homework.lesson6.agency.model.Property
import homework.lesson6.agency.service.client.PropertiesClient
import homework.lesson6.agency.service.repo.SoldPropertiesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class AgencyService(
    private val propertiesClient: PropertiesClient,
    private val soldPropertiesDao: SoldPropertiesDao
) {

    fun addSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest) {
        CoroutineScope(Dispatchers.Default).launch {
            val id = addSoldPropertyRequest.id
            val property = propertiesClient.getProperty(id) ?: propertyNotFound(id)
            withContext(Dispatchers.IO) { soldPropertiesDao.add(property) }
        }
    }

    fun getSoldProperty(id: Int): Property = soldPropertiesDao.get(id) ?: propertyNotFound(id)

    private fun propertyNotFound(id: Int): Nothing =
        throw IllegalArgumentException("The property with id: $id not found")

}

