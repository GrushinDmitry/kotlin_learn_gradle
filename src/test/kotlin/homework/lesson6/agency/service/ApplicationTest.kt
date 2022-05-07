package homework.lesson6.agency.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import homework.lesson6.agency.model.AddPropertyAndGetIdResponse
import homework.lesson6.agency.model.AddSoldPropertyRequest
import homework.lesson6.agency.model.Property
import homework.lesson6.agency.model.Status
import homework.lesson6.agency.service.client.PropertiesClient
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import kotlinx.coroutines.delay
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import kotlin.random.Random

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest(
    private val mockMvc: MockMvc, private val objectMapper: ObjectMapper
) : FeatureSpec() {

    @MockkBean
    private lateinit var propertiesClient: PropertiesClient


    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeTest(testCase: TestCase) {
        coEvery { propertiesClient.getProperty(any()) } answers { properties.find { it.id == firstArg() } }
    }

    init {
        feature("add and getPropertyById property with coroutine") {
            scenario("status = done and property added after the time") {
                val addPropertyResponse = addSoldProperty(propertyLeningrad.id)
                addSoldProperty(propertySmolensk.id)
                addSoldProperty(propertyChelyabinsk.id)
                val requestStatus = addPropertyResponse.status
                val requestNumber = addPropertyResponse.requestNumber

                requestStatus shouldBe Status.PROCESSING
                delay(200)
                val getIdResponse = getIdByRequestNumber(requestNumber)
                val propertyId = getIdResponse.propertyId
                propertyId shouldNotBe null
                getIdResponse.status shouldBe Status.DONE
                getSoldProperty(propertyId!!) shouldBe getPropertyLeningradExpected(propertyId)
            }
            scenario("immediately reports data ok") {
                getStatusAddSoldProperty(propertySmolensk.id) shouldBe okRequestExpected
            }
            scenario("http.status = ok, status adding property = error in the absence of property") {
                val notFoundSoldPropertyId = properties.maxOf { it.id } + 1
                val addPropertyResponse = addSoldProperty(notFoundSoldPropertyId)
                val requestStatus = addPropertyResponse.status
                val requestNumber = addPropertyResponse.requestNumber

                requestStatus shouldBe Status.PROCESSING
                getIdByRequestNumber(requestNumber).status shouldBe Status.ERROR
            }
            scenario("correct entity out after the time to add property") {
                val requestNumber = addSoldProperty(propertyArkhangelsk.id).requestNumber
                delay(200)
                val propertyId = getIdByRequestNumber(requestNumber).propertyId!!

                getSoldProperty(propertyId) shouldBe getPropertyArkhangelskExpected(propertyId)
            }
            scenario("http.status = bad because unknown property") {
                val incorrectNumberRequest = 0

                getStatusGetSoldProperty(incorrectNumberRequest) shouldBe badRequestExpected
            }
            scenario("http.status = ok for getting first number") {
                getStatusGetSoldProperty(1) shouldBe okRequestExpected
            }
        }
    }

    fun addSoldProperty(id: Int): AddPropertyAndGetIdResponse =
        mockMvc.post("/soldProperty/sold") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(AddSoldPropertyRequest(id))
        }.readResponse()

    fun getStatusAddSoldProperty(id: Int): Int =
        mockMvc.post("/soldProperty/sold") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(AddSoldPropertyRequest(id))
        }.andReturn().response.status

    fun getSoldProperty(id: Int): Property = mockMvc.get("/soldProperty/{id}", id).readResponse()

    fun getStatusGetSoldProperty(id: Int): Int = mockMvc.get(
        "/soldProperty/{id}", id
    ).andReturn().response.status

    fun getIdByRequestNumber(number: Int): AddPropertyAndGetIdResponse =
        mockMvc.get("/soldProperty/request-number?requestNumber=$number").readResponse()

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T =
        this.andExpect { status { isEqualTo(expectedStatus.value()) } }
            .andReturn().response.getContentAsString(Charsets.UTF_8)
            .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }

    private val propertyTula = Property(
        Random.nextInt(1000), "Тульская обл., г. Ступино, въезд Космонавтов, 97", 32, 1000500
    )
    private val propertyChelyabinsk = Property(
        Random.nextInt(1000), "Челябинская область, город Истра, Гоголевский бульвар, 96", 55, 1500500
    )
    private val propertySmolensk = Property(
        Random.nextInt(1000), "Смоленская область, г. Зарайск, пер. Ленина, 64", 15, 500500
    )
    private val propertyArkhangelsk = Property(
        Random.nextInt(1000), "Архангельская область, город Шатура, пер. Чехова, 53", 17, 900500
    )
    private val propertyLeningrad = Property(
        Random.nextInt(1000), "Ленинградская область, город Дорохово, наб. Гагарина, 18", 1000, 110000000
    )

    private val properties =
        setOf(propertyTula, propertyChelyabinsk, propertySmolensk, propertyArkhangelsk, propertyLeningrad)

    private fun getPropertyLeningradExpected(id: Int) = Property(
        id, propertyLeningrad.address, propertyLeningrad.area, propertyLeningrad.price
    )

    private fun getPropertyArkhangelskExpected(id: Int) = Property(
        id, propertyArkhangelsk.address, propertyArkhangelsk.area, propertyArkhangelsk.price
    )

    private val badRequestExpected: Int = HttpStatus.BAD_REQUEST.value()
    private val okRequestExpected: Int = HttpStatus.OK.value()
}

