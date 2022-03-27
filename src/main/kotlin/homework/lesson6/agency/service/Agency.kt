package homework.lesson6.agency.service

import homework.lesson6.agency.Accounting
import homework.lesson6.agency.model.*
import org.springframework.stereotype.Service

@Service
class Agency(
    private val baseService: BaseService,
    private val accounting: Accounting
) {

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
            BuyResponse(property, Status.READY, change)
        } catch (e: Exception) {
            BuyResponse(item = null, Status.DECLINED, cash, e.message)
        }

    fun returnById(buyingId: Int): BuyingById =
        baseService.getBuyingById(buyingId)

}