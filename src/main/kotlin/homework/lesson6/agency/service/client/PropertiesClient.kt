package homework.lesson6.agency.service.client

import homework.lesson6.agency.model.Property
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull

@Service
class PropertiesClient(
    private val webClient: WebClient,
    @Value("\${properties.address}") private val propertiesAddress: String
) {
    suspend fun getProperty(id: Int): Property? =
        webClient.get()
            .uri("$propertiesAddress$PROPERTY_BY_ID", id)
            .retrieve()
            .awaitBodyOrNull()
}

private const val PROPERTY_BY_ID = "/soldProperty?identification={id}"
