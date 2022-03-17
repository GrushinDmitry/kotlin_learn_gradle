package homework.lesson4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
    fun returnException() {

        assertThrows<NoSuchElementException>(stringStack::pop)
        assertThrows<NoSuchElementException>(intStack::pop)
        assertThrows<NoSuchElementException>(stringStack::peek)
        assertThrows<NoSuchElementException>(intStack::peek)
    }


    @ParameterizedTest
    @ValueSource(ints = [1, 50])
    fun pushPeek(iteration:Int) {
        val listObj = arrayOfNulls<String>(iteration)

        val valueRandom = listObj.map { Random.nextInt(1000).toString() }
        valueRandom.map(stringStack::push)
        val actualSizePush = stringStack.getSize()
        val actualPeek = listObj.map { stringStack.peek() }
        val actualSizePeek = stringStack.getSize()

        val expectedSizePushPeek = when {
            iteration < 8 -> 16
            else -> (2.0.pow(floor(log2(iteration.toDouble()) + 2.0))).toInt()
        }
        val expectedPeek = listObj.map { valueRandom.last() }
        assertEquals(expectedSizePushPeek, actualSizePush)
        assertEquals(expectedPeek, actualPeek)
        assertEquals(expectedSizePushPeek, actualSizePeek)
    }


    @ParameterizedTest
    @ValueSource(ints = [1, 299])
    fun pushPop(iteration: Int) {
        val listObj = arrayOfNulls<Int>(iteration)

        val valueRandom = listObj.map { Random.nextInt(100000) }
        valueRandom.map(intStack::push)
        val actualSizePush = intStack.getSize()
        val actualPop = listObj.map { intStack.pop() }
        val actualSizePop = intStack.getSize()

        val expectedSizePush = when {
            iteration < 8 -> 16
            else -> (2.0.pow(floor(log2(iteration.toDouble()) + 2.0))).toInt()
        }
        val expectedPop = valueRandom.reversed()
        val expectedSizePop = 16
        assertEquals(expectedSizePush, actualSizePush)
        assertEquals(expectedPop, actualPop)
        assertEquals(expectedSizePop, actualSizePop)

    }


}