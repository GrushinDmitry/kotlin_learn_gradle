package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Service


@Service
@Profile("jdbc")
private class JdbcDao(val jdbcTemplate: JdbcTemplate) : SoldPropertiesDao {

    override fun add(property: Property): Property {
        val id = SimpleJdbcInsert(jdbcTemplate)
            .withTableName("sold_properties")
            .usingColumns("address", "area", "price")
            .usingGeneratedKeyColumns("id")
            .executeAndReturnKey(BeanPropertySqlParameterSource(property)).toInt()
        return get(id)!!
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
}

private const val sqlQueryDelete = "delete from sold_properties where id = ?"
private const val sqlQueryFind = "select * from sold_properties where price < ? order by price limit ? offset ?"
private const val sqlQueryGet = "select * from sold_properties where id = ?"



