package homework.lesson6.agency.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import homework.lesson6.agency.model.AddSoldPropertyRequest
import homework.lesson6.agency.model.Property
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
    private var currentId = 0

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeTest(testCase: TestCase) {
        coEvery { propertiesClient.getProperty(any()) } answers { properties.find { it.id == firstArg() } }
    }

    init {
        feature("add and get property with coroutine") {
            scenario("correct http.status after the time to add property") {
                currentId = addSoldProperty(AddSoldPropertyRequest(propertyLeningrad.id))+1
                getStatusGetSoldProperty(currentId) shouldBe badRequest
                delay(200)
                getStatusGetSoldProperty(currentId) shouldBe okRequest
            }
            scenario("immediately reports data ok") {
                getStatusAddSoldProperty(AddSoldPropertyRequest(propertySmolensk.id)) shouldBe okRequest
            }
            scenario("reports data ok in the absence of property") {
                getStatusAddSoldProperty(AddSoldPropertyRequest(properties.maxByOrNull { it.id }!!.id + 1)) shouldBe okRequest
            }
            scenario("correct entity out after the time to add property") {
                currentId = addSoldProperty(AddSoldPropertyRequest(propertyArkhangelsk.id))+1
                delay(200)
                getSoldProperty(currentId) shouldBe getPropertyArkhangelskExpected(currentId)
            }
            scenario("http.status = bad because unknown property") {
                getStatusGetSoldProperty(currentId + 1) shouldBe badRequest
            }
        }
    }

    fun addSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest) = mockMvc.post("/soldProperty/sold") {
        contentType = MediaType.APPLICATION_JSON
        content = objectMapper.writeValueAsString(addSoldPropertyRequest)
    }.readResponse<Int>()

    fun getStatusAddSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest): Int =
        mockMvc.post("/soldProperty/sold") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(addSoldPropertyRequest)
        }.andReturn().response.status


    fun getSoldProperty(id: Int): Property = mockMvc.get("/soldProperty/{id}", id).readResponse()

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

    private val badRequest: Int = HttpStatus.BAD_REQUEST.value()
    private val okRequest: Int = HttpStatus.OK.value()
}

