package homework.lesson6.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.convert.DurationUnit
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration
import java.time.temporal.ChronoUnit


@Configuration
class ServiceConfiguration (val clientConfig: ClientConfig) {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder
        .setConnectTimeout(clientConfig.connectTimeoutInSeconds)
        .setReadTimeout(clientConfig.readTimeoutInSeconds)
        .build()
}
@ConstructorBinding
@ConfigurationProperties(prefix="agency.client")
data class ClientConfig(
    @DurationUnit(ChronoUnit.SECONDS)
    val connectTimeoutInSeconds: Duration,
    @DurationUnit(ChronoUnit.SECONDS)
    val readTimeoutInSeconds: Duration
)


