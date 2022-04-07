package homework.lesson7.agency.service.client

import homework.lesson7.agency.model.Property
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject


@Service
class PropertiesClient(
    private val restTemplate: RestTemplate,
    @Value("\${properties.address}") private val propertiesAddress: String
) {


    fun getProperty(id: Int): Property? = try {
        restTemplate.getForObject("$propertiesAddress$PROPERTY_BY_ID", id)
    } catch (e: NotFound) {
        null
    }
}

private const val PROPERTY_BY_ID = "/soldProperty?identification={id}"
