package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JpaSoldPropertiesRepository : JpaRepository<Property, Int> {

    @Query(value = "SELECT TOP 1 ID FROM sold_properties ORDER BY ID DESC", nativeQuery = true)
    fun getId(): Int?
}
