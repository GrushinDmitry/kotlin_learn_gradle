package homework.lesson7.configuration

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@Configuration
class ServiceConfiguration(val clientConfig: ClientConfig) {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder
        .setConnectTimeout(clientConfig.connectTimeoutInSeconds)
        .setReadTimeout(clientConfig.readTimeoutInSeconds)
        .build()
}


