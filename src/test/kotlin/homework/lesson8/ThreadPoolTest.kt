package homework.lesson8

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec

class ThreadPoolTest : FeatureSpec() {

    init {
        feature("test init") {
            scenario("fail size") {
                shouldThrow<IllegalArgumentException> { ThreadPool(0) }
                shouldThrow<IllegalArgumentException> { ThreadPool(9) }
            }

        }
    }
}