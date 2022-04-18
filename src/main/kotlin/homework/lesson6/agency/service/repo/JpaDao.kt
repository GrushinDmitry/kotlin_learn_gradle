package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
@Profile("jpa")
private class JpaDao(private val repository: JpaSoldPropertiesRepository) : SoldPropertiesDao {

    override fun add(property: Property): Property { println (repository.saveAndFlush(property)); return repository.save(property)}


    override fun deleteById(id: Int): Property? {
        val propertyDeleted = repository.getById(id)
        repository.deleteById(id)
        return propertyDeleted
    }

    override fun find(priceMax: Int, pageNum: Int, pageSize: Int): List<Property> =
        repository.findAll(PageRequest.of((pageNum - 1), pageSize, Sort.by("price")))
            .content.filter { it.price < priceMax }

    override fun get(id: Int): Property? = repository.getById(id)

    override fun getId(): Int {
        TODO("Not yet implemented")
    }
}
