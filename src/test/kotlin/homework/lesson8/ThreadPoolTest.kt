package homework.lesson8

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import kotlin.random.Random

class ThreadPoolTest : FeatureSpec() {

    init {
        feature("test init") {
            scenario("fail size") {
                shouldThrow<IllegalArgumentException> { ThreadPool(0) }
                shouldThrow<IllegalArgumentException> { ThreadPool(9) }
            }
            feature("testing work") {

                scenario("success execute after shutdown") {
                    val repeatRandom = Random.nextInt(10)
                    val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                    val threadPool = ThreadPool(sizeThreadPoolRandom)

                    threadPool.shutdown()
                    val actualMap = getActualMap(repeatRandom, threadPool, 0)

                    val expectedMap = emptyMap<Int, Int>()
                    actualMap shouldBe expectedMap
                }

                scenario("success shutdown after empty execute") {
                    val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                    val threadPool = ThreadPool(sizeThreadPoolRandom)
                    threadPool.execute { }
                    threadPool.shutdown()
                }

                scenario("success threads>=tasks with sleep") {
                    val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                    val valueForTasksLessOrEqualsThread = Random.nextInt(1)
                    val valueTasks = sizeThreadPoolRandom - valueForTasksLessOrEqualsThread
                    val threadPool = ThreadPool(sizeThreadPoolRandom)

                    val actualMap = getActualMap(valueTasks, threadPool)

                    val expectedMap = getExpectedMap(valueTasks)
                    actualMap shouldBe expectedMap
                    threadPool.shutdown()
                }

                scenario("success threads<tasks with sleep") {
                    val sizeThreadPoolRandom = 1 + Random.nextInt(7)
                    val valueTasks = sizeThreadPoolRandom + 1
                    val threadPool = ThreadPool(sizeThreadPoolRandom)

                    val actualMap = getActualMap(valueTasks, threadPool)

                    val expectedMap = getExpectedMap(valueTasks)
                    actualMap shouldBe expectedMap
                    threadPool.shutdown()
                }
            }
        }
    }
}

private fun getActualMap(valueRepeat: Int, threadPool: ThreadPool, timeSleep: Long = 1000): MutableMap<Int, Int> {
    val actualMap = mutableMapOf<Int, Int>()
    repeat(valueRepeat) {
        threadPool.execute {
            actualMap[it] = it
        }
        Thread.sleep(timeSleep)
    }
    return actualMap
}

private fun getExpectedMap(valueRepeat: Int): MutableMap<Int, Int> {
    val expectedMap = mutableMapOf<Int, Int>()
    repeat(valueRepeat) {
        expectedMap[it] = it
    }
    return expectedMap
}