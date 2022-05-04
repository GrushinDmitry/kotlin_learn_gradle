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
    private val requestNumberToPropertyId = ConcurrentHashMap<Int, AddingProcess>()

    fun addSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest): AddingProcessRequest {
        requestNumberToPropertyId[requestNumber.incrementAndGet()] =
            AddingProcess(null, AddingProcessRequest(requestNumber.get()))
        CoroutineScope(Dispatchers.Default).launch {
            val fixedRequestNumber = requestNumber.get()
            when (val property = propertiesClient.getProperty(addSoldPropertyRequest.id)) {
                null -> requestNumberToPropertyId[fixedRequestNumber] =
                    AddingProcess(null, AddingProcessRequest(fixedRequestNumber, Status.ERROR))
                else -> {
                    withContext(Dispatchers.IO) {
                        val fixedRequestNumber = requestNumber.get()
                        requestNumberToPropertyId[fixedRequestNumber] = AddingProcess(
                            soldPropertiesDao.add(property),
                            AddingProcessRequest(fixedRequestNumber, Status.DONE)
                        )
                    }
                }
            }
        }
        return requestNumberToPropertyId[requestNumber.get()]!!.addingProcessRequest
    }

    fun getSoldProperty(number: Int): PropertyRequest {
        if (number > requestNumber.get() || number < 1) throw IllegalArgumentException("The request for number: $number not found")
        return getPropertyRequest(number)
    }

    fun getPropertyRequest(number: Int): PropertyRequest {
        val addingProcessRequest = requestNumberToPropertyId[number]!!.addingProcessRequest
        return when (addingProcessRequest.status) {
            Status.DONE -> {
                val id = requestNumberToPropertyId[number]!!.id!!
                PropertyRequest(
                    addingProcessRequest, soldPropertiesDao.get(id)
                )
            }
            else -> PropertyRequest(addingProcessRequest, null)
        }
    }
}



