package homework.lesson8

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import kotlin.random.Random

class ThreadPoolTest : FeatureSpec() {

    private val mockJob = mockk<() -> Runnable>(relaxed = true)


    override fun afterAny(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("test init") {
            scenario("fail size") {
                shouldThrow<IllegalArgumentException> { ThreadPool(0) }
                shouldThrow<IllegalArgumentException> { ThreadPool(9) }
            }
        }
        feature("testing work") {
            scenario("job not executed after shutdown") {
                val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                val threadPool = ThreadPool(sizeThreadPoolRandom)

                threadPool.shutdown()

                shouldThrow<IllegalStateException> { threadPool.execute { mockJob() } }
                verify(exactly = 0) { mockJob() }
            }
            scenario("job executed before shutdown") {
                val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                val threadPool = ThreadPool(sizeThreadPoolRandom)

                threadPool.execute { mockJob() }
                threadPool.shutdown()

                verify(exactly = 1) { mockJob() }
            }
            scenario("success work") {
                val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                val valueTasks = Random.nextInt(15)
                val threadPool = ThreadPool(sizeThreadPoolRandom)

                repeat(valueTasks) {
                    threadPool.execute { mockJob() }
                }

                verify(exactly = valueTasks) { mockJob() }
                threadPool.shutdown()
            }
        }
    }
}


