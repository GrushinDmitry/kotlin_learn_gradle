package homework.lesson6.agency.model

data class BuyResponse<Property>(
    val item: Property? = null,
    val id: Int?,
    val status: Status,
    val change: Int = 0,
    val comment: String? = null
)

data class BuyingById(
    val id: Int,
    val property: Property,
    val status: Status = Status.COMPLETED
)

enum class Status { READY, DECLINED, COMPLETED }
