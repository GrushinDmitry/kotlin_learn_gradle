package homework.lesson4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random
import kotlin.random.nextUInt

internal class MyQueueTest {
    private val stringQueue = MyQueue<String>(2)
    private val uintQueue = MyQueue<UInt>(2)


    @Test
    fun returnException() {

        assertThrows<NoSuchElementException>(stringQueue::remove)
        assertThrows<NoSuchElementException>(uintQueue::remove)
        assertThrows<NoSuchElementException>(stringQueue::element)
        assertThrows<NoSuchElementException>(uintQueue::element)
    }


    @Test
    fun returnNull() {
        assertNull(stringQueue.peek())
        assertNull(uintQueue.poll())
        assertNull(stringQueue.peek())
        assertNull(uintQueue.poll())
    }

    @Test
    fun offerPeekElement() {
        val listObj = listOf("2", "1", "9")

        val actualOffer = listObj.map(stringQueue::offer)
        val actualPeek = listObj.map { stringQueue.peek() }
        val actualElement = listObj.map { stringQueue.element() }

        val expectedOffer = listOf(true, true, false)
        val expectedPeekElement = listOf("2", "2", "2")
        assertEquals(expectedOffer, actualOffer)
        assertEquals(expectedPeekElement, actualPeek)
        assertEquals(expectedPeekElement, actualElement)
    }


    @Test
    fun offerRemove() {
        val iteration = 1000
        val uintQueue = MyQueue<UInt>(iteration)
        val listObj = listOf(1..iteration)

        val valueRandom = listObj.map { Random.nextUInt(100000u) }
        valueRandom.map(uintQueue::offer)
        val actualRemove = listObj.map { uintQueue.remove() }

        val expectedRemove = valueRandom
        assertEquals(expectedRemove, actualRemove)
        assertThrows<NoSuchElementException>(uintQueue::remove)
    }

    @Test
    fun offerPoll() {
        val iteration = 10000
        val stringQueue = MyQueue<String>(iteration)
        val listObj = listOf(1..iteration)

        val valueRandom = listObj.map { Random.nextUInt(1000000u).toString() }
        valueRandom.map(stringQueue::offer)
        val actualList = listObj.map { stringQueue.poll() }
        val actualRemove = actualList.plusElement(stringQueue.poll())

        val expectedRemove = valueRandom.plusElement(null)
        assertEquals(expectedRemove, actualRemove)

    /*    val iteration = 1
        var actualPoll = listOf<UInt?>()
        var expectedPoll = listOf<UInt?>()
        val uintQueue = MyQueue<UInt>(iteration)

        for (i in 1..iteration) {
            val value = Random.nextUInt(100000u)
            uintQueue.offer(value)
            if (i <= iteration) {
                expectedPoll = expectedPoll.plusElement(value)
            }
        }
        for (i in 1..iteration) {
            actualPoll = actualPoll.plusElement(uintQueue.poll())
        }
        actualPoll = actualPoll.plusElement(uintQueue.poll())

        expectedPoll = expectedPoll.plusElement(null)
        assertEquals(expectedPoll, actualPoll)*/
    }

    @Test
    fun cicleQueue() {
        uintQueue.offer(5u)
        uintQueue.offer(10u)
        uintQueue.poll()
        uintQueue.offer(11u)
        val actual = listOf(uintQueue.poll(), uintQueue.poll())

        val expected = listOf(10u, 11u)
        assertEquals(expected, actual)
    }
}


