package homework.lesson6.agency

import homework.lesson6.agency.model.AddSoldPropertyRequest
import homework.lesson6.agency.model.AddingProcessRequest
import homework.lesson6.agency.model.PropertyRequest
import homework.lesson6.agency.service.AgencyService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/soldProperty")
class SoldPropertyController(private val agencyService: AgencyService) {

    @PostMapping("/sold")
    fun addSoldProperty(@RequestBody addSoldPropertyRequest: AddSoldPropertyRequest): AddingProcessRequest =
        agencyService.addSoldProperty(addSoldPropertyRequest)

    @GetMapping("/{id}")
    fun getSoldProperty(@PathVariable id: Int): PropertyRequest = agencyService.getSoldProperty(id)
}