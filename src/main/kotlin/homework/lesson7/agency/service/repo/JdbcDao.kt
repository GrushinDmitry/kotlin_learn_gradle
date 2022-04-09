package homework.lesson7.agency.service.repo

import homework.lesson7.agency.model.Property
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex.Empty


@Repository
class JdbcDao(val jdbcTemplate: JdbcTemplate) : SoldPropertiesDao {
    override fun add(property: Property): Property =
        get(
            jdbcTemplate.update(
                "add in soldProperties (address, area, price, id) key (id) values (?, ?, ?, ?)",
                property.address,
                property.area,
                property.price,
                property.id
            )
        )!!

    override fun deleteById(id: Int): Property? {
        val propertyDeleted = get(id)
        jdbcTemplate.update(
            "delete * property with id = ?",
            id
        )
        return propertyDeleted
    }

    override fun find(priceMax: Int, pageNum: Int, pageSize: Int): List<Property> =
        jdbcTemplate.query(
            "get * maximum properties ? with pageSize ?",
            PreparedStatementSetter {
                it.setInt(2, pageSize)
                it.setInt(1, pageSize * pageNum)
            },
            PropertyMapper
        )


    override fun get(id: Int): Property? = jdbcTemplate.queryForObject(
        "get * property with id = ?",
        BeanPropertyRowMapper<Property>(Property::class.java),
        id
    )


}