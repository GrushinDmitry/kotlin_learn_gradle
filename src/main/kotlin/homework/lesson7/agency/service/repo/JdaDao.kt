package homework.lesson7.agency.service.repo

import homework.lesson7.agency.model.Property
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository

@Primary
@Repository
class JdaDao(private val jdaSoldPropertiesRepository: JdaSoldPropertiesRepository) : SoldPropertiesDao {

    override fun add(property: Property): Property = jdaSoldPropertiesRepository.save(property)

    override fun deleteById(id: Int): Property? {
        val propertyDeleted = get(id)
        jdaSoldPropertiesRepository.deleteById(id)
        return propertyDeleted
    }

    override fun find(priceMax: Int, pageNum: Int, pageSize: Int): List<Property> =
        jdaSoldPropertiesRepository.findAll(PageRequest.of(pageNum, pageNum, Sort.by("price")))
            .content.filter { it.price < priceMax }


    override fun get(id: Int): Property? = jdaSoldPropertiesRepository.getById(id)
}