package homework.lesson6.agency

import homework.lesson6.agency.model.BuyResponse
import homework.lesson6.agency.model.BuyingById
import homework.lesson6.agency.model.Property
import homework.lesson6.agency.model.PropertyBaseItem
import homework.lesson6.agency.service.Agency
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/property")
class PropertyController(private val agency: Agency) {

    @GetMapping("/base")
    fun getPropertyBase(): Set<PropertyBaseItem> =
        agency.getPropertyBase()

    @PostMapping("/buy")
    fun buyProperty(@RequestParam address: String, @RequestParam cash: Int): BuyResponse<Property> =
        agency.buyProperty(address, cash)

    @PostMapping("/buyer")
    fun welcomeBuyer(@RequestBody fullName: String): String =
        "Hello, $fullName"

    @GetMapping("/buy/{buyingId}")
    fun returnById(@PathVariable buyingId: Int): BuyingById =
        agency.returnById(buyingId)
}