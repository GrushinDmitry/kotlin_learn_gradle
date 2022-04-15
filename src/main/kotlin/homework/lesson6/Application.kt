package homework.lesson6

import homework.lesson6.configuration.ClientConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ClientConfig::class)
class Application

fun main() {
    runApplication<Application>()
}
