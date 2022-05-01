package homework.lesson6.agency.service.repo

import homework.lesson6.agency.model.Property

interface SoldPropertiesDao {

    fun add(property: Property): Int

    fun get(id: Int): Property?
}