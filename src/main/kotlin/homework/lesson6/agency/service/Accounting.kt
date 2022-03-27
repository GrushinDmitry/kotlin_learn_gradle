package homework.lesson6.agency

import homework.lesson6.agency.model.BuyingById
import homework.lesson6.agency.model.Property
import homework.lesson6.agency.service.client.Ledger
import org.springframework.stereotype.Service

@Service
class Accounting(private val ledger: Ledger) {

    fun getPropertyPrice(property: Property): Int = ledger.getPropertyPrice(property)

    fun buyProperty(property: Property, cash: Int): Pair<BuyingById, Int> {
        val price = getPropertyPrice(property)
        return buy(property, price, cash)
    }

    private fun buy(property: Property, price: Int, money: Int): Pair<BuyingById, Int> {
        require(money >= price) { "Недостаточно денег!" }
        val buyingId = ledger.saveBuying(price)
        val buy = BuyingById(buyingId, property)
        val change = money - price
        return buy to change
    }
}