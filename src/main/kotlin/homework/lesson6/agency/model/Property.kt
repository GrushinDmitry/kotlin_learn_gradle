package homework.lesson6.agency.model

import javax.persistence.*

@Entity
@Table(name = "sold_properties")
data class Property(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val address: String,
    val area: Int,
    val price: Int,
)

data class PropertyRequest(
    val addingProcessRequest: AddingProcessRequest,
    val property: Property? = null
)




