package homework.lesson6.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.convert.DurationUnit
import java.time.Duration
import java.time.temporal.ChronoUnit

@ConstructorBinding
@ConfigurationProperties
data class ClientConfig(
    val connectTimeoutInMillis: Int,
    @DurationUnit(ChronoUnit.SECONDS)
    val responseTimeoutInSeconds: Duration
)