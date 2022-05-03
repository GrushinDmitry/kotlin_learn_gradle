package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
@Primary
private class JpaDao(private val repository: JpaSoldPropertiesRepository) : SoldPropertiesDao {

    override fun add(property: Property): Int? = try {
        repository.saveAndFlush(property).id
    } catch (e: Exception) {
        null
    }

    override fun get(id: Int): Property? = try {
        repository.getById(id)
    } catch (e: EntityNotFoundException) {
        null
    }
}

