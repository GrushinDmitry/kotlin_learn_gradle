package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Service
import java.sql.Statement

@Service
@Profile("jdbc")
private class JdbcDao(val jdbcTemplate: JdbcTemplate) : SoldPropertiesDao {
    private val keyHolder = GeneratedKeyHolder()

    override fun add(property: Property): Property {
        jdbcTemplate.update(
            { connection ->
                connection
                    .prepareStatement(
                        sqlQueryAdd, Statement.RETURN_GENERATED_KEYS
                    ).apply {
                        setString(1, property.address)
                        setInt(2, property.area)
                        setInt(3, property.price)
                    }
            },
            keyHolder
        )
        return get(keyHolder.key!!.toInt())
            ?: throw IllegalStateException("Property not added")
    }

    override fun deleteById(id: Int): Property? {
        val deletedSoldProperty = get(id)
        jdbcTemplate.update(sqlQueryDelete, id)
        return deletedSoldProperty
    }

    override fun find(priceMax: Int, pageNum: Int, pageSize: Int): List<Property> = jdbcTemplate.query(
        sqlQueryFind, DataClassRowMapper(Property::class.java), priceMax, pageSize, pageSize * (pageNum - 1)
    )

    override fun get(id: Int): Property? = jdbcTemplate.queryForObject(
        sqlQueryGet, DataClassRowMapper(Property::class.java), id
    )

    override fun getId(): Int = jdbcTemplate.queryForObject(sqlQueryGetId, Int::class.java)!!
}

private const val sqlQueryAdd = "insert into sold_properties (address, area, price) values (?, ?, ?)"
private const val sqlQueryDelete = "delete from sold_properties where id = ?"
private const val sqlQueryFind = "select * from sold_properties where price < ? order by price limit ? offset ?"
private const val sqlQueryGet = "select * from sold_properties where id = ?"
private const val sqlQueryGetId = "SELECT CURRENT VALUE FOR public.default"

