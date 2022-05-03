package homework.lesson6.agency.model

data class AddingProcessRequest(
    val id: Int?,
    val status: Status = Status.PROCESSING
)

enum class Status { PROCESSING, DONE, ERROR }