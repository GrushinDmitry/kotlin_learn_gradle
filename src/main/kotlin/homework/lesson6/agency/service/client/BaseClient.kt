package homework.lesson6.agency.service.client

import homework.lesson6.agency.model.Property
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.getForObject


@Service
class BaseClient(
    private val restTemplate: RestTemplate,
    @Value("\${base.address}") private val baseAddress: String
) {

    fun getPropertyBase(): Set<Property> =
        restTemplate.exchange<Set<Property>>("$baseAddress$GET_BASE", HttpMethod.GET).body.orEmpty()

    fun getProperty(address: String): Property? = try {
        restTemplate.getForObject("$baseAddress$GET_PROPERTY_BY_ADDRESS", address.lowercase())
    } catch (e: NotFound) {
        null
    }
}

private const val GET_BASE = "/property"
private const val GET_PROPERTY_BY_ADDRESS = "/property/{address}"
