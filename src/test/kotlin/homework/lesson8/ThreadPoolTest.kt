package homework.lesson8

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import kotlin.random.Random

class ThreadPoolTest : FeatureSpec() {

    private val mockJob = mockk<Runnable>()

    override fun afterAny(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("test init") {
            scenario("fail size") {
                shouldThrow<IllegalArgumentException> { ThreadPool(0) }
                shouldThrow<IllegalArgumentException> { ThreadPool(9) }
            }
            feature("testing work") {

                scenario("job not executed after shutdown") {
                    val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                    val threadPool = ThreadPool(sizeThreadPoolRandom)

                    threadPool.shutdown()

                    shouldThrow<IllegalStateException> { threadPool.execute { println(mockJob.toString()) } }
                    verify(exactly = 0) { println(mockJob.toString()) }
                }

                scenario("job executed before shutdown") {
                    val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                    val threadPool = ThreadPool(sizeThreadPoolRandom)

                    threadPool.execute { println(mockJob.toString()) }
                    threadPool.shutdown()

                    verify(exactly = 1) { println(mockJob.toString()) }
                }

                scenario("thread pool is interrupted due to an issue:no answer found for task") {
                    val sizeThreadPoolRandom = 8
                    val valueTasks = 5
                    val threadPool = ThreadPool(sizeThreadPoolRandom)

                    repeat(valueTasks) {
                        threadPool.execute { mockJob.run() } shouldBe println("Thread pool is interrupted due to an issue: no answer found for: Runnable(#1).run()")
                    }
                    verify(exactly = valueTasks) { mockJob.run() }
                    threadPool.shutdown()
                }
                scenario("success work") {
                    val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                    val valueTasks = Random.nextInt(15)
                    val threadPool = ThreadPool(sizeThreadPoolRandom)

                    repeat(valueTasks) {
                        threadPool.execute { println(mockJob.toString()) }
                    }

                    verify(exactly = valueTasks) { println(mockJob.toString()) }
                    threadPool.shutdown()
                }
            }
        }
    }
}

