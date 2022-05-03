package homework.lesson6.agency.service

import homework.lesson6.agency.model.*
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
    private val propertiesClient: PropertiesClient, private val soldPropertiesDao: SoldPropertiesDao
) {
    private var requestNumber = AtomicInteger(0)
    private val requestNumberToPropertyId = ConcurrentHashMap<Int, AddingProcessRequest>()

    fun addSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest): AddingProcessRequest {
        requestNumberToPropertyId[requestNumber.incrementAndGet()] = AddingProcessRequest(null)
        CoroutineScope(Dispatchers.Default).launch {
            val id = addSoldPropertyRequest.id
            val property = propertiesClient.getProperty(id)
            property?.let {
                withContext(Dispatchers.IO) {
                    val fixedRequestNumber = requestNumber.get()
                    requestNumberToPropertyId[fixedRequestNumber] =
                        AddingProcessRequest(soldPropertiesDao.add(it), status = getAddingProcessStatus(it))
                }
            }
        }
        return requestNumberToPropertyId[requestNumber.get()]!!
    }

    fun getSoldProperty(number: Int): PropertyRequest {
        if (number > requestNumber.get() || number < 1) throw IllegalArgumentException("The request for number: $number not found")
        /*  ?: throw AddingInSoldPropertiesException("Adding in sold properties failed")*/
        return getPropertyRequest(number)
    }

    fun getAddingProcessStatus(property: Property): Status = when (soldPropertiesDao.add(property)) {
        null -> Status.ERROR
        else -> Status.DONE
    }

    fun getPropertyRequest(number: Int): PropertyRequest {
        val addingProcessRequest=requestNumberToPropertyId[number]!!
        return when (addingProcessRequest.status) {
            Status.DONE -> {
                PropertyRequest(addingProcessRequest, soldPropertiesDao.get(addingProcessRequest.id!!))
            }
            else -> PropertyRequest(addingProcessRequest, null)
        }
    }
}



