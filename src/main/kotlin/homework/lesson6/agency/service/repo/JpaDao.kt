package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
@Primary
private class JpaDao(private val repository: JpaSoldPropertiesRepository) : SoldPropertiesDao {

    override fun add(property: Property): Int = repository.saveAndFlush(property).id

    override fun deleteById(id: Int): Property? {
        val propertyDeleted = repository.getById(id)
        repository.deleteById(id)
        return propertyDeleted
    }

    override fun find(priceMax: Int, pageNum: Int, pageSize: Int): List<Property> =
        repository.findByPriceLessThan(priceMax, PageRequest.of((pageNum - 1), pageSize, Sort.by("price")))

    override fun get(id: Int): Property? = repository.getById(id)
}

