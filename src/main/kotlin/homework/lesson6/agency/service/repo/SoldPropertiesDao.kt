package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property

interface SoldPropertiesDao {

    fun add(property: Property): Int

    fun deleteById(id: Int): Property?

    fun find(priceMax: Int, pageNum: Int, pageSize: Int): List<Property>

    fun get(id: Int): Property?
}