package homework.lesson8

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.mockk.mockk
import io.mockk.verify
import kotlin.random.Random

class ThreadPoolTest : FeatureSpec() {

    private val mockJob = mockk<Runnable>()

    init {
        feature("test init") {
            scenario("fail size") {
                shouldThrow<IllegalArgumentException> { ThreadPool(0) }
                shouldThrow<IllegalArgumentException> { ThreadPool(9) }
            }
            feature("testing work") {

                scenario("success execute after shutdown") {
                    val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                    val threadPool = ThreadPool(sizeThreadPoolRandom)

                    threadPool.shutdown()
                    threadPool.execute { mockJob.toString() }

                    verify(exactly = 0) { mockJob.toString() }
                }

                scenario("success shutdown after empty execute") {
                    val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                    val threadPool = ThreadPool(sizeThreadPoolRandom)

                    threadPool.execute { mockJob.toString() }
                    threadPool.shutdown()

                    verify(exactly = 1) { mockJob.toString() }
                }

                scenario("success work") {
                    val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                    val valueTasks = Random.nextInt(15)
                    val threadPool = ThreadPool(sizeThreadPoolRandom)

                    repeat(valueTasks) {
                        threadPool.execute { mockJob.toString() }
                    }

                    verify(exactly = valueTasks + 1) { mockJob.toString() }
                    threadPool.shutdown()
                }
            }
        }
    }
}

