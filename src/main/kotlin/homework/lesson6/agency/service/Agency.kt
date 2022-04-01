package homework.lesson6.agency.service

import homework.lesson6.agency.model.*
import homework.lesson6.agency.service.client.BaseClient
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class Agency(private val baseClient: BaseClient) {

    fun getPropertyBase(): Set<PropertyBaseItem> =
        baseService.getPropertyBase().map { property ->
            val price = accounting.getPropertyPrice(property)
            PropertyBaseItem(property, price)
        }.toSet()

    fun buyProperty(address: String, cash: Int): BuyResponse<Property> =
        try {
            val property = baseService.getProperty(address)
            val (buying, change) = accounting.buyProperty(property, cash)
            baseService.buying(buying)
            BuyResponse(property, buying.id, Status.READY, change)
        } catch (e: Exception) {
            BuyResponse(item = null, id = null, Status.DECLINED, cash, e.message)
        }

    fun returnById(buyingId: Int): BuyingById =
        baseService.getBuyingById(buyingId)

    fun findProperty(price: Int, pageNum: Int, pageSize: Int): List<Property> {

    }

    fun saveProperty(property: Property): Property {

    }

    fun delPropertyById(id: Int) {

    }

}

/*
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


    fun delPropertyById(id: Int): BuyingById {
        val buying = buyings[buyingId]
        return requireNotNull(buying) { "Нет такой покупки!" }
    }


}*/
