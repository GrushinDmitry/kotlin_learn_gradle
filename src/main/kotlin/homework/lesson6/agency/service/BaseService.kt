package homework.lesson6.agency.service

import homework.lesson6.agency.model.BuyingById
import homework.lesson6.agency.model.Property
import homework.lesson6.agency.service.client.BaseClient
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class BaseService(private val baseClient: BaseClient) {

    private val buyings = ConcurrentHashMap<Int, BuyingById>()

    fun getPropertyBase(): Set<Property> = baseClient.getPropertyBase()

    fun getProperty(address: String): Property {
        val coffee = baseClient.getProperty(address)
        return requireNotNull(coffee) { "Нет недвижимости с таким адресом!" }
    }


    fun buying(buying: BuyingById) {
        buyings[buying.id] = buying
    }


    fun getBuyingById(buyingId: Int): BuyingById {
        val buying = buyings[buyingId]
        return requireNotNull(buying) { "Нет такой покупки!" }
    }


}