package homework.lesson7.agency.service.repo

import homework.lesson7.agency.model.Property
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JdaSoldPropertiesRepository: JpaRepository<Property,Int> {
}