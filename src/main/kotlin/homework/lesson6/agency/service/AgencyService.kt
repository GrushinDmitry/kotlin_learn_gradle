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
    var numberRequest: Int = 0
    val matchingIdWithNumber = mutableMapOf<Int, Int?>()

    fun addSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest): Int {
        matchingIdWithNumber[++numberRequest] = null
        CoroutineScope(Dispatchers.Default).launch {
            val id = addSoldPropertyRequest.id
            val property = propertiesClient.getProperty(id)
            property?.let {
                withContext(Dispatchers.IO) {
                    matchingIdWithNumber[numberRequest] = soldPropertiesDao.add(it)
                }
            }
        }
        return numberRequest
    }

    fun getSoldProperty(number: Int): Property {
        if (number > numberRequest || number < 1) throw IllegalArgumentException("The request for number: $number not found")
        val id = matchingIdWithNumber[number] ?: throw AddingInSoldPropertiesException("Adding in sold properties failed")
        return soldPropertiesDao.get(id)!!
    }
}