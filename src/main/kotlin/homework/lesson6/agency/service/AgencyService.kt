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
    private var requestNumber = AtomicInteger(0)
    private val requestNumberToPropertyId = ConcurrentHashMap<Int, AddPropertyAndGetIdResponse>()

    fun addSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest): AddPropertyAndGetIdResponse {
        requestNumberToPropertyId[requestNumber.incrementAndGet()] =
            AddPropertyAndGetIdResponse(requestNumber.get())
        CoroutineScope(Dispatchers.Default).launch {
            val fixedRequestNumber = requestNumber.get()
            when (val property = propertiesClient.getProperty(addSoldPropertyRequest.id)) {
                null -> requestNumberToPropertyId[fixedRequestNumber] =
                    AddPropertyAndGetIdResponse(fixedRequestNumber, Status.ERROR)
                else -> {
                    withContext(Dispatchers.IO) {
                        val fixedRequestNumber = requestNumber.get()
                        requestNumberToPropertyId[fixedRequestNumber] = AddPropertyAndGetIdResponse(
                            fixedRequestNumber, Status.DONE,
                            soldPropertiesDao.add(property)
                        )
                    }
                }
            }
        }
        return requestNumberToPropertyId[requestNumber.get()]!!
    }

    fun getSoldProperty(id: Int): Property =
        soldPropertiesDao.get(id) ?: throw IllegalArgumentException("The property with id: $id not found")

    fun getIdByRequestNumber(number: Int): AddPropertyAndGetIdResponse {
        if (number > requestNumber.get() || number < 1) throw IllegalArgumentException("The request for number: $number not found")
        return requestNumberToPropertyId[number]!!
    }
}