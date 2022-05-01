package homework.lesson6.agency

import homework.lesson6.agency.model.AddSoldPropertyRequest
import homework.lesson6.agency.model.Property
import homework.lesson6.agency.service.AgencyService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/soldProperty")
class SoldPropertyController(private val agencyService: AgencyService) {

    @PostMapping("/sold")
    fun addSoldProperty(@RequestBody addSoldPropertyRequest: AddSoldPropertyRequest): Int =
        agencyService.addSoldProperty(addSoldPropertyRequest)

    @GetMapping("/{id}")
    fun getSoldProperty(@PathVariable id: Int): Property = agencyService.getSoldProperty(id)
}