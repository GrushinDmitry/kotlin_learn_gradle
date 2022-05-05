package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaSoldPropertiesRepository : JpaRepository<Property, Int>{

    fun findByPriceLessThan (maxPrice:Int, pageable: Pageable): List<Property>
}
