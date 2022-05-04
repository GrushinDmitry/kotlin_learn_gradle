package homework.lesson6.agency.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import homework.lesson6.agency.model.*
import homework.lesson6.agency.service.client.PropertiesClient
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
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
    private var requestNumber = 0

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeTest(testCase: TestCase) {
        coEvery { propertiesClient.getProperty(any()) } answers { properties.find { it.id == firstArg() } }
    }

    init {
        feature("add and get property with coroutine") {
            scenario("status = done and property added after the time") {
                val addingProcessRequest = addSoldProperty(AddSoldPropertyRequest(propertyLeningrad.id))
                addSoldProperty(AddSoldPropertyRequest(propertySmolensk.id))
                addSoldProperty(AddSoldPropertyRequest(propertyChelyabinsk.id))
                val requestStatus = addingProcessRequest.status
                requestNumber = addingProcessRequest.requestNumber

                requestStatus shouldBe Status.PROCESSING
                getStatusGetSoldProperty(requestNumber) shouldBe okRequestExpected
                delay(200)
                val gettingSoldProperty = getSoldProperty(requestNumber)
                val idExpected = gettingSoldProperty.property!!.id
                gettingSoldProperty.addingProcessRequest.status shouldBe Status.DONE
                gettingSoldProperty.property shouldBe getPropertyLeningradExpected(idExpected)
            }
            scenario("immediately reports data ok") {
                getStatusAddSoldProperty(AddSoldPropertyRequest(propertySmolensk.id)) shouldBe okRequestExpected
            }
            scenario("http.status = ok, status adding property = error in the absence of property") {
                val addingNotFoundSoldProperty = AddSoldPropertyRequest(properties.maxByOrNull { it.id }!!.id + 1)
                val addingProcessRequest = addSoldProperty(addingNotFoundSoldProperty)
                val requestStatus = addingProcessRequest.status
                requestNumber = addingProcessRequest.requestNumber

                requestStatus shouldBe Status.PROCESSING
                getSoldProperty(requestNumber).addingProcessRequest.status shouldBe Status.ERROR
                getStatusAddSoldProperty(addingNotFoundSoldProperty) shouldBe okRequestExpected
            }
            scenario("correct entity out after the time to add property") {
                requestNumber = addSoldProperty(AddSoldPropertyRequest(propertyArkhangelsk.id)).requestNumber
                delay(200)
                val idExpected = getSoldProperty(requestNumber).property!!.id

                getSoldProperty(requestNumber).property shouldBe getPropertyArkhangelskExpected(idExpected)
            }
            scenario("http.status = bad because unknown property") {
                val incorrectNumberRequestMax = requestNumber + 1
                val incorrectNumberRequestMin = 0

                getStatusGetSoldProperty(incorrectNumberRequestMax) shouldBe badRequestExpected
                getStatusGetSoldProperty(incorrectNumberRequestMin) shouldBe badRequestExpected
            }
            scenario("http.status = ok for getting first number") {
                getStatusGetSoldProperty(1) shouldBe okRequestExpected
            }
        }
    }

    fun addSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest) = mockMvc.post("/soldProperty/sold") {
        contentType = MediaType.APPLICATION_JSON
        content = objectMapper.writeValueAsString(addSoldPropertyRequest)
    }.readResponse<AddingProcessRequest>()

    fun getStatusAddSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest): Int =
        mockMvc.post("/soldProperty/sold") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(addSoldPropertyRequest)
        }.andReturn().response.status

    fun getSoldProperty(id: Int): PropertyRequest = mockMvc.get("/soldProperty/{id}", id).readResponse()

    fun getStatusGetSoldProperty(id: Int): Int = mockMvc.get(
        "/soldProperty/{id}", id
    ).andReturn().response.status

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

    fun getPropertyLeningradExpected(id: Int) = Property(
        id, propertyLeningrad.address, propertyLeningrad.area, propertyLeningrad.price
    )

    fun getPropertyArkhangelskExpected(id: Int) = Property(
        id, propertyArkhangelsk.address, propertyArkhangelsk.area, propertyArkhangelsk.price
    )

    fun getPropertySmolenskExpected(id: Int) = Property(
        id, propertySmolensk.address, propertySmolensk.area, propertySmolensk.price
    )

    fun getFailGettingExpected(number: Int) = "The request for number: $number not found"

    val badRequestExpected: Int = HttpStatus.BAD_REQUEST.value()
    private val okRequestExpected: Int = HttpStatus.OK.value()

}

