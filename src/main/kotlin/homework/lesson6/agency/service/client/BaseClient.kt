package homework.lesson6.agency.service.client

import homework.lesson6.agency.model.Property
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject


@Service
class BaseClient(
    private val restTemplate: RestTemplate,
    @Value("\${base.address}") private val baseAddress: String
) {

   /* fun delPropertyBase(): Set<Property> =
        restTemplate.exchange<Set<Property>>("$baseAddress$GET_PROPERTY_BY_ID", HttpMethod.DELETE).body.orEmpty()*/


    fun getProperty(id: Int): Property? = try {
        restTemplate.getForObject("$baseAddress$GET_PROPERTY_BY_ID", id)
    } catch (e: NotFound) {
        null
    }
}

private const val GET_PROPERTY_BY_ID = "/property?identification={id}"
