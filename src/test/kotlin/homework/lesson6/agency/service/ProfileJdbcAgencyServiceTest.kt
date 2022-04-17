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
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("jdbc")
class ProfileJdbcAgencyServiceTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
) : FeatureSpec() {

    @MockkBean
    private lateinit var propertiesClient: PropertiesClient

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeEach(testCase: TestCase) {
        every { propertiesClient.getProperty(any()) } answers { properties.find { it.id == firstArg() } }
    }

    init {
        feature("add property in SoldPropertiesDao") {
            scenario("success") {
                addSoldProperty(AddSoldPropertyRequest(5)) shouldBe propertyLeningrad
                getSoldProperty(5) shouldBe propertyLeningrad
            }
            scenario("failure - unknown property") {
                getStatusAddSoldProperty(AddSoldPropertyRequest(100)) shouldBe badRequest
            }
        }
        feature("get property from SoldPropertiesDao") {
            scenario("success") {
                getSoldProperty(1) shouldBe propertyTula
                getSoldProperty(4) shouldBe propertyArkhangelsk
            }
            scenario("failure - unknown property") {
                getStatusGetSoldProperty(100) shouldBe badRequest
            }
        }
        feature("find sold property with pagination") {
            scenario("success:empty and noEmpty") {
                findSoldPropertyByPrice(500499, 1, 1) shouldBe emptyList()
                findSoldPropertyByPrice(500550, 1, 1) shouldBe listOf(propertySmolensk)
                findSoldPropertyByPrice(1000000, 1, 2) shouldBe listOf(propertySmolensk, propertyArkhangelsk)
            }
            scenario("failure - illegalArguments") {
                getStatusFindSoldPropertyByPrice(0, 1, 1) shouldBe badRequest
                getStatusFindSoldPropertyByPrice(1, 0, 1) shouldBe badRequest
                getStatusFindSoldPropertyByPrice(1, 1, 0) shouldBe badRequest

            }
        }
        feature("delete sold property from SoldPropertiesDao") {
            scenario("success") {
                deleteSoldPropertyById(5) shouldBe propertyLeningrad
                getStatusDeleteSoldPropertyById(5) shouldBe badRequest

            }
            scenario("failure - no such sold property") {
                getStatusDeleteSoldPropertyById(100) shouldBe badRequest
            }
        }
    }

    fun addSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest): Property =
        mockMvc.post("/soldProperty/sold") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(addSoldPropertyRequest)
        }.readResponse()

    fun getStatusAddSoldProperty(addSoldPropertyRequest: AddSoldPropertyRequest): Int =
        mockMvc.post("/soldProperty/sold") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(addSoldPropertyRequest)
        }.andReturn().response.status

    fun getSoldProperty(id: Int): Property = mockMvc.get("/soldProperty/{id}", id).readResponse()

    fun getStatusGetSoldProperty(id: Int): Int = mockMvc.get(
        "/soldProperty/{id}", id
    ).andReturn().response.status

    fun findSoldPropertyByPrice(maxPrice: Int, pageNum: Int, pageSize: Int): List<Property> =
        mockMvc.get(
            "/soldProperty/find?maxPrice={maxPrice}&pageSize={pageSize}&pageNum={pageNum}",
            maxPrice, pageSize, pageNum
        ).readResponse()

    fun getStatusFindSoldPropertyByPrice(maxPrice: Int, pageNum: Int, pageSize: Int): Int =
        mockMvc.get(
            "/soldProperty/find?maxPrice={maxPrice}&pageSize={pageSize}&pageNum={pageNum}",
            maxPrice, pageSize, pageNum
        ).andReturn().response.status

    fun deleteSoldPropertyById(id: Int): Property = mockMvc.delete("/soldProperty/{id}", id).readResponse()

    fun getStatusDeleteSoldPropertyById(id: Int): Int = mockMvc.delete(
        "/soldProperty/{id}", id
    ).andReturn().response.status

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T = this
        .andExpect { status { isEqualTo(expectedStatus.value()) } }
        .andReturn().response.getContentAsString(Charsets.UTF_8)
        .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }

    private val propertyTula = Property(
        1, "Тульская обл., г. Ступино, въезд Космонавтов, 97", 32, 1000500
    )
    private val propertyChelyabinsk = Property(
        2, "Челябинская область, город Истра, Гоголевский бульвар, 96", 55, 1500500
    )
    private val propertySmolensk = Property(
        3, "Смоленская область, г. Зарайск, пер. Ленина, 64", 15, 500500
    )
    private val propertyArkhangelsk = Property(
        4, "Архангельская область, город Шатура, пер. Чехова, 53", 17, 900500
    )
    private val propertyLeningrad = Property(
        5, "Ленинградская область, город Дорохово, наб. Гагарина, 18", 1000, 110000000
    )
    private val properties =
        setOf(propertyTula, propertyChelyabinsk, propertySmolensk, propertyArkhangelsk, propertyLeningrad)

    private val badRequest: Int = HttpStatus.BAD_REQUEST.value()
}