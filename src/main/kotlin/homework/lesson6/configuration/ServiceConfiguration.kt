package homework.lesson6.configuration

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit


@Configuration
class ServiceConfiguration(private val clientConfig: ClientConfig) {

    @Bean
    fun client(): HttpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfig.connectTimeoutInSeconds.toMillis().toInt())
        .doOnConnected {
            it.addHandler(ReadTimeoutHandler(clientConfig.readTimeoutInSeconds.seconds, TimeUnit.SECONDS))
        }

    @Bean
    fun webClient(httpClient: HttpClient): WebClient =
        WebClient.builder().clientConnector(ReactorClientHttpConnector(httpClient)).build()
}
