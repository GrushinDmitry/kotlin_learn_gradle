package homework.lesson7.agency.service.repo

import homework.lesson7.agency.model.Property
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.stereotype.Repository

@Profile("jdbc")
//@Primary
@Repository
class JdbcDao(val jdbcTemplate: JdbcTemplate) : SoldPropertiesDao {
    override fun add(property: Property): Property =
        get(
            jdbcTemplate.update(
                "merge into soldproperties (address, area, price, id) key (id) values (?, ?, ?, ?)",
                property.address,
                property.area,
                property.price,
                property.id
            )
        )!!

    override fun deleteById(id: Int): Property? {
        val propertyDeleted = get(id)
        jdbcTemplate.update(
            "delete from soldproperties where id = ?",
            id
        )
        return propertyDeleted
    }

    override fun find(priceMax: Int, pageNum: Int, pageSize: Int): List<Property> =
        jdbcTemplate.query(
            "select * from soldproperties where price < ? order by price limit ? offset ?",
            PreparedStatementSetter {
                it.setInt(1, priceMax)
                it.setInt(2, pageSize)
                it.setInt(3, pageSize * (pageNum-1))
            },
            DataClassRowMapper(Property::class.java)
        )


    override fun get(id: Int): Property? = jdbcTemplate.queryForObject(
        "select * from soldproperties where id = ?",
        DataClassRowMapper(Property::class.java),
        id
    )

}