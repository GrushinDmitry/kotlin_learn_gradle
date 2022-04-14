package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Profile("dev", "test")
@Repository
class DevSoldPropertiesDao(private val soldPropertiesDao: SoldPropertiesDao) : SoldPropertiesDao {

    override fun add(property: Property): Property = soldPropertiesDao.add(property)

    override fun deleteById(id: Int): Property? = soldPropertiesDao.deleteById(id)

    override fun find(priceMax: Int, pageNum: Int, pageSize: Int): List<Property> =
        soldPropertiesDao.find(priceMax, pageNum, pageSize)

    override fun get(id: Int): Property? = soldPropertiesDao.get(id)
}