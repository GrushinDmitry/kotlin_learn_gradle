package homework.lesson6.agency


import homework.lesson6.agency.model.Property
import homework.lesson6.agency.service.AgencyService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/soldProperty")
class SoldPropertyController(private val agencyService: AgencyService) {

    @PostMapping("/add")
    fun soldProperty(@RequestBody id: Int): Property = agencyService.addSoldProperty(id)


    @GetMapping("/{id}")
    fun getSoldProperty(@PathVariable id: Int): Property = agencyService.getSoldProperty(id)

    @GetMapping("/find")
    fun findSoldPropertyByPrice(
        @RequestParam maxPrice: Int = 1000000,
        @RequestParam pageNum: Int = 2,
        @RequestParam pageSize: Int = 2,
    ): List<Property> = agencyService.findSoldPropertiesByMaxPrice(maxPrice, pageNum, pageSize)

    @DeleteMapping("/{id}")
    fun deleteSoldPropertyById(@PathVariable id: Int): Property = agencyService.deleteSoldPropertyById(id)


}