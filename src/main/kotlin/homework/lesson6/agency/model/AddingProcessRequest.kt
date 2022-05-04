package homework.lesson6.agency.model

data class AddingProcess(val id: Int?, val addingProcessRequest: AddingProcessRequest)

data class AddingProcessRequest(
    val requestNumber: Int,
    val status: Status = Status.PROCESSING
)

enum class Status { PROCESSING, DONE, ERROR }