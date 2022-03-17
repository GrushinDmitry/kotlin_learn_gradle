package homework.lesson4

class MyStack<E : Any> {
    private val initSize = 16
    private var stackArray = arrayOfNulls<Any>(initSize)
    val size: Int
        get() = stackArray.size
    var head = -1


    fun push(item: E) {
        if (isHalfFull()) resize(stackArray.size * 2)
        stackArray[++head] = item
    }

    fun pop(): E {
        when {
            isEmpty() -> throw NoSuchElementException()
            !isHalfFull() && stackArray.size > initSize -> resize(stackArray.size / 2)
        }
        return stackArray[head--] as E
    }

    fun peek(): E? = if (isEmpty()) null else stackArray[head] as E


    private fun resize(newSize: Int) {
        stackArray = stackArray.copyOf(newSize)
    }


    private fun isEmpty(): Boolean = head == -1

    private fun isHalfFull(): Boolean = head >= stackArray.size / 2 - 2


}
