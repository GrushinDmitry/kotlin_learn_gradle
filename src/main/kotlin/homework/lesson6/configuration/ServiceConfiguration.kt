package homework.lesson6.configuration

import io.netty.channel.ChannelOption
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient


@Configuration
class ServiceConfiguration(private val clientConfig: ClientConfig) {

    var client: HttpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfig.connectTimeoutInMillis)
        .responseTimeout(clientConfig.responseTimeoutInSeconds)

    @Bean
    fun webClientConfig(): WebClient {
        return WebClient
            .builder()
            .clientConnector(ReactorClientHttpConnector(client))
            .build()
    }

}
