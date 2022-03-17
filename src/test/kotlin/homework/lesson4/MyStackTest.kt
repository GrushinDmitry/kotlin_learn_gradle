package homework.lesson4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.floor
import kotlin.math.log2
import kotlin.math.pow
import kotlin.random.Random

internal class MyStackTest {

    private val stringStack = MyStack<String>()
    private val intStack = MyStack<Int>()


    @Test
    fun returnExceptionNull() {

        assertAll(
            { assertThrows<NoSuchElementException>(stringStack::pop) },
            { assertThrows<NoSuchElementException>(intStack::pop) },
        )
        assertAll(
            { assertNull(stringStack.peek()) },
            { assertNull(intStack.peek()) }
        )

    }


    @ParameterizedTest
    @ValueSource(ints = [7, 8, 9, 16, 500])
    fun pushPeek(capacity: Int) {
        val range = 1..capacity
        val valueRandom = range.map { Random.nextInt(1000).toString() }
        valueRandom.map(stringStack::push)
        val actualSizePush = stringStack.size
        val actualPeek = range.map { stringStack.peek() }
        val actualSizePeek = stringStack.size

        val expectedSizePushPeek = calculateStackSizeByCapacity(capacity)

        val expectedPeek = range.map { valueRandom.last() }
        assertEquals(expectedSizePushPeek, actualSizePush)
        assertEquals(expectedPeek, actualPeek)
        assertEquals(expectedSizePushPeek, actualSizePeek)
    }


    @ParameterizedTest
    @ValueSource(ints = [7, 8, 9, 16, 60000])
    fun pushPop(capacity: Int) {
        val range = 1..capacity
        val valueRandom = range.map { Random.nextInt(100000) }
        valueRandom.map(intStack::push)
        val actualSizePush = intStack.size
        val actualPop = range.map { intStack.pop() }
        val actualSizePop = intStack.size

        val expectedSizePush = calculateStackSizeByCapacity(capacity)
        val expectedPop = valueRandom.reversed()
        val expectedSizePop = 16
        assertEquals(expectedSizePush, actualSizePush)
        assertEquals(expectedPop, actualPop)
        assertEquals(expectedSizePop, actualSizePop)

    }

    private fun calculateStackSizeByCapacity(capacity: Int) = when {
        capacity < 8 -> 16
        else -> (2.0.pow(floor(log2(capacity.toDouble()) + 2.0))).toInt()
    }


}