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
    private var requestNumber: Int = 0
    private val requestNumberToPropertyId = mutableMapOf<Int, Int?>()

    fun addSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest): Int {
        ++requestNumber
        CoroutineScope(Dispatchers.Default).launch {
            val id = addSoldPropertyRequest.id
            val property = propertiesClient.getProperty(id)
            property?.let {
                withContext(Dispatchers.IO) {
                    val fixedRequestNumber = requestNumber
                    requestNumberToPropertyId[fixedRequestNumber] = soldPropertiesDao.add(it)
                }
            }
        }
        return requestNumber
    }

    fun getSoldProperty(number: Int): Property {
        if (number > requestNumber || number < 1) throw IllegalArgumentException("The request for number: $number not found")
        val id = requestNumberToPropertyId[number]
            ?: throw AddingInSoldPropertiesException("Adding in sold properties failed")
        return soldPropertiesDao.get(id)!!
    }
}