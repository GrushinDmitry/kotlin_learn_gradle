package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
private class JpaDao(private val repository: JpaSoldPropertiesRepository) : SoldPropertiesDao {

    override fun add(property: Property) {
        repository.saveAndFlush(property)
    }

    override fun get(id: Int): Property? = repository.getById(id)
}

