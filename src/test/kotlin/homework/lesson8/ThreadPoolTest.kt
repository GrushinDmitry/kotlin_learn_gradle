package homework.lesson8

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import java.lang.Thread.sleep
import kotlin.random.Random

class ThreadPoolTest : FeatureSpec() {

    private val mockTask = mockk<Runnable>()

    override fun beforeEach(testCase: TestCase) {
        every { mockTask.run() } answers { sleep(200) }
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
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
                val threadPoolSizeRandom = 1 + Random.nextInt(7)
                val threadPool = ThreadPool(threadPoolSizeRandom)

                threadPool.shutdown()
                shouldThrow<IllegalStateException> { threadPool.execute(mockTask) }

                verify(exactly = 0) { mockTask.run() }
            }
            scenario("job executed before shutdown") {
                val threadPoolSizeRandom = 1 + Random.nextInt(7)
                val threadPool = ThreadPool(threadPoolSizeRandom)

                threadPool.execute(mockTask)
                delay(500)
                threadPool.shutdown()

                verify(exactly = 1) { mockTask.run() }
            }
            scenario("success work") {
                val threadPoolSize = 8
                val taskCount = 16
                val threadPool = ThreadPool(threadPoolSize)

                repeat(taskCount) {
                    threadPool.execute(mockTask)
                }

                verify(exactly = threadPoolSize) { mockTask.run() }
                delay(1000)
                verify(exactly = taskCount) { mockTask.run() }
            }
        }
    }
}

