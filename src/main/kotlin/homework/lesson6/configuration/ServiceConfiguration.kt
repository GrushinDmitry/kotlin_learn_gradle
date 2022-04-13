package homework.lesson6.configuration

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.client.RestTemplate
import javax.sql.DataSource


@Configuration
class ServiceConfiguration(val clientConfig: ClientConfig) {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder
        .setConnectTimeout(clientConfig.connectTimeoutInSeconds)
        .setReadTimeout(clientConfig.readTimeoutInSeconds)
        .build()
}

@Configuration
@EnableJpaRepositories(basePackages = ["homework.lesson6"])
class DbConfiguration {

    @Bean
    fun jdbcTemplate(dataSource: DataSource): JdbcTemplate = JdbcTemplate(dataSource)
}

