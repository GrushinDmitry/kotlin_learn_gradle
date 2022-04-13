package homework.lesson6.agency


import homework.lesson6.agency.model.AddSoldPropertyRequest
import homework.lesson6.agency.model.Property
import homework.lesson6.agency.service.DevSoldPropertiesDao
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/soldProperty")
class SoldPropertyController(private val devSoldPropertiesDao: DevSoldPropertiesDao) {

    @PostMapping("/sold")
    fun addSoldProperty(@RequestBody addSoldPropertyRequest: AddSoldPropertyRequest): Property =
        devSoldPropertiesDao.addSoldProperty(addSoldPropertyRequest)


    @GetMapping("/{id}")
    fun getSoldProperty(@PathVariable id: Int): Property = devSoldPropertiesDao.getSoldProperty(id)

    @GetMapping("/find")
    fun findSoldPropertyByPrice(
        @RequestParam maxPrice: Int = 1000000,
        @RequestParam pageNum: Int = 2,
        @RequestParam pageSize: Int = 2,
    ): List<Property> = devSoldPropertiesDao.findSoldPropertiesByMaxPrice(maxPrice, pageNum, pageSize)

    @DeleteMapping("/{id}")
    fun deleteSoldPropertyById(@PathVariable id: Int): Property = devSoldPropertiesDao.deleteSoldPropertyById(id)


}