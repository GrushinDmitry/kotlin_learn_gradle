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
        var actualOffer = listOf<Boolean>()
        var actualPeek = listOf<String?>()
        var actualElement = listOf<String>()

        listObj.forEach {
            actualOffer = actualOffer.plusElement(stringQueue.offer(it))
            actualPeek = actualPeek.plusElement(stringQueue.peek())
            actualElement = actualElement.plusElement(stringQueue.element())
        }

        val expectedOffer = listOf(true, true, false)
        val expectedPeekElement = listOf("2", "2", "2")
        assertEquals(expectedOffer, actualOffer)
        assertEquals(expectedPeekElement, actualPeek)
        assertEquals(expectedPeekElement, actualElement)
    }


    @Test
    fun offerRemove() {
        val iteration = 1000
        var actualRemove = listOf<UInt?>()
        var expectedRemove = listOf<UInt?>()
        val uintQueue = MyQueue<UInt>(iteration)

        for (i in 1..iteration) {
            val value = Random.nextUInt(100000u)
            uintQueue.offer(value)
            if (i <= iteration) {
                expectedRemove = expectedRemove.plusElement(value)
            }
        }
        for (i in 1..iteration) {
            actualRemove = actualRemove.plusElement(uintQueue.poll())
        }
        actualRemove = actualRemove.plusElement(uintQueue.poll())

        expectedRemove = expectedRemove.plusElement(null)
        assertEquals(expectedRemove, actualRemove)
    }

    @Test
    fun offerPoll() {
        val iteration = 1
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
        assertEquals(expectedPoll, actualPoll)
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