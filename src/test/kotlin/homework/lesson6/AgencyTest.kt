package homework.lesson6

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import homework.lesson6.agency.model.*
import homework.lesson6.agency.service.client.BaseClient
import homework.lesson6.agency.service.client.Ledger
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import kotlin.random.Random.Default.nextInt
import kotlin.text.Charsets.UTF_8

@SpringBootTest
@AutoConfigureMockMvc
class AgencyTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    @MockkBean
    private lateinit var baseClient: BaseClient

    @MockkBean
    private lateinit var ledger: Ledger

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeEach(testCase: TestCase) {
        every { baseClient.getPropertyBase() } returns propertyBase
        every { baseClient.getProperty(any()) } answers { propertyBase.find { it.address == firstArg() } }
        every { ledger.getPropertyPrice(any()) } answers { propertyPrices.getValue(firstArg<Property>().address) }
        every { ledger.saveBuying(any()) } returns nextInt()
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("buying property") {
            scenario("success") {
                val propertyBase = getPropertyBase()
                val property = propertyBase.first()
                val cash = property.price + 1

                val buy = buyProperty(property.address, cash)

                buy should {
                    it.item!!.address shouldBe property.address
                    it.status shouldBe Status.READY
                    it.change shouldBe (cash - property.price)
                    it.comment shouldBe null
                }
            }
            scenario("failure - unknown property") {
                val propertyAddress = "г. Рязань, ул. Высоковольтная, 5"
                val cash = Int.MAX_VALUE

                val buy = buyProperty(propertyAddress, cash)

                buy should {
                    it.item shouldBe null
                    it.status shouldBe Status.DECLINED
                    it.change shouldBe cash
                    it.comment shouldBe "Нет недвижимости с таким адресом!"
                }
            }
            scenario("failure - insufficient cash") {
                val propertyBase = getPropertyBase()
                val property = propertyBase.first()
                val cash = property.price - 1

                val buy = buyProperty(property.address, cash)

                buy should {
                    it.item shouldBe null
                    it.status shouldBe Status.DECLINED
                    it.change shouldBe cash
                    it.comment shouldBe "Недостаточно денег!"
                }
            }
            scenario("success buyingId") {
                val propertyBase = getPropertyBase()
                val property = propertyBase.first()
                val cash = property.price + 1

                val buy = buyProperty(property.address, cash)
                val buyingId = buy.id

                buyingId.shouldNotBeNull()
                buy.change shouldBe (cash - property.price)


                returnById(buyingId) should {
                    it.property.address shouldBe property.address
                    it.status shouldBe Status.COMPLETED
                }
            }
        }
    }


    private fun getPropertyBase(): Set<PropertyBaseItem> = mockMvc.get("/property/base").readResponse()

    private fun buyProperty(address: String, cash: Int, status: HttpStatus = HttpStatus.OK): BuyResponse<Property> =
        mockMvc.post("/property/buy?address={address}&cash={cash}", address, cash).readResponse(status)

    private fun returnById(buyingId: Int): BuyingById = mockMvc.get("/property/buy/{buyingId}", buyingId).readResponse()

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T =
        this.andExpect { status { isEqualTo(expectedStatus.value()) } }.andReturn().response.getContentAsString(UTF_8)
            .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }

    private val propertyBase = setOf(
        Property("г. Рыбное, ул. Новоселов, 13, 45", 50), Property("г. Москва, ул. Ленина, 45, 119", 100)
    )


    private val propertyPrices = mapOf(
        "г. Рыбное, ул. Новоселов, 13, 45" to 1000000, "г. Москва, ул. Ленина, 45, 119" to 5000000
    )
}