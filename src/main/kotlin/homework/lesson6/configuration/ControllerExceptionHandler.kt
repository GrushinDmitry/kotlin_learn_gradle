package homework.lesson6.configuration

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException
import javax.persistence.EntityNotFoundException

@RestControllerAdvice
class ControllerExceptionHandler {

    private val log = LoggerFactory.getLogger(ControllerExceptionHandler::class.java)

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException): Map<String, String> {
        log.warn(e.message, e)
        return response(e)
    }

    @Profile("jpa")
    @ExceptionHandler(JpaObjectRetrievalFailureException::class, EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleJpaBadRequest(e: Exception): Map<String, String> {
        log.warn(e.message, e)
        return response(e)
    }

    @Profile("jdbc")
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleEmptyResultDataAccessException(e: EmptyResultDataAccessException): Map<String, String> {
        log.warn(e.message, e)
        return response(e)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleExceptionNotFound(e: HttpClientErrorException.NotFound): Map<String, String> {
        val pageNotFound = "The page was dragged by the kraken to the seabed"
        log.error(e.message, e)
        return response(e, pageNotFound)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleExceptionIntServerErr(e: Exception): Map<String, String> {
        log.error(e.message, e)
        return response(e)
    }

    private fun response(e: Exception, userMessage: String = ""): Map<String, String> = mapOf(
        "exception" to e.javaClass.simpleName,
        "message" to e.message.orEmpty(),
        "userMessage" to userMessage
    )
}
