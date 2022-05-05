package homework.lesson6.agency.model

data class AddPropertyAndGetIdResponse(
    val requestNumber: Int,
    val status: Status = Status.PROCESSING,
    val propertyId: Int? = null
)

enum class Status { PROCESSING, DONE, ERROR }