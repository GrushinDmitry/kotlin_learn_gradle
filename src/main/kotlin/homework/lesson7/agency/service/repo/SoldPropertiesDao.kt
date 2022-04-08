package homework.lesson7.agency.service.repo

import homework.lesson7.agency.model.Property



interface SoldPropertiesDao {
    fun add(property: Property): Property

    fun deleteById(id: Int): Property?

    fun find(priceMax: Int, pageNum: Int, pageSize: Int): List<Property>

    fun get(id: Int): Property?
}