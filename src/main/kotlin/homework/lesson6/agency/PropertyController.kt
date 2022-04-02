package homework.lesson6.agency


import homework.lesson6.agency.model.Property
import homework.lesson6.agency.service.Agency
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/property")
class PropertyController(private val agency: Agency) {

    @PostMapping("/save")
    fun saveProperty(@RequestBody id: Int): String {
        agency.saveProperty(id)
        return "Save property with id: $id completed"
    }

    @PostMapping("/get/{id}")
    fun getProperty(@PathVariable id: Int): Property = agency.getProperty(id)

    @PostMapping("/find")
    fun findPropertyByPrice(
        @RequestParam price: Int,
        @RequestParam pageNum: Int,
        @RequestParam pageSize: Int,
    ): List<Property> = agency.findProperty(price, pageNum, pageSize)

    @DeleteMapping("/del/{id}")
    fun delPropertyById(@PathVariable id: Int): String {
        agency.delPropertyById(id)
        return "Delete property with id: $id completed"
    }

}