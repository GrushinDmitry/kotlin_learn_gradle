package homework.lesson6.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ServiceConfiguration() {

    @Bean
    fun webClientConfig(): WebClient {
        return WebClient.builder().build()
    }

}

