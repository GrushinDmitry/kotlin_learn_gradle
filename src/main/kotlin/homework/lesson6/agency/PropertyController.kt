package homework.lesson6.agency


import homework.lesson6.agency.model.Property
import homework.lesson6.agency.service.Agency
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/soldProperty")
class PropertyController(private val agency: Agency) {

    @PostMapping("/add")
    fun soldProperty(@RequestBody id: Int): Property = agency.addSoldProperty(id)


    @GetMapping("/{id}")
    fun getSoldProperty(@PathVariable id: Int): Property = agency.getSoldProperty(id)

    @GetMapping("/find")
    fun findSoldPropertyByPrice(
        @RequestParam maxPrice: Int = 1000000,
        @RequestParam pageNum: Int = 2,
        @RequestParam pageSize: Int = 2,
    ): List<Property> = agency.findSoldPropertiesByMaxPrice(maxPrice, pageNum, pageSize)

    @DeleteMapping("/{id}")
    fun delSoldPropertyById(@PathVariable id: Int): Property = agency.delSoldPropertyById(id)


}