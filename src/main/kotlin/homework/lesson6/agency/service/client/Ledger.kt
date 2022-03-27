package homework.lesson6.agency.service.client

import homework.lesson6.agency.model.Property
import homework.lesson6.agency.model.external.SaveBuyingRequest
import homework.lesson6.agency.model.external.SaveBuyingResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForObject


@Service
class Ledger(
    private val restTemplate: RestTemplate,
    @Value("\${ledger.address}") private val ledgerAddress: String
) {

    fun getPropertyPrice(property: Property): Int =
        restTemplate.getForObject("$ledgerAddress$GET_PRICE", "property", property.address)!!

    fun saveBuying(price: Int): Int {
        val request = SaveBuyingRequest(price)
        return restTemplate.postForObject<SaveBuyingResponse?>("$ledgerAddress$SAVE_ORDER", request)!!.buyingId
    }
}

private const val GET_PRICE = "/price?property={property}&address={address}"
private const val SAVE_ORDER = "/buying"


