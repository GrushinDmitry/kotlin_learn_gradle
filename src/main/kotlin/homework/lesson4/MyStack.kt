package homework.lesson4

class MyStack<E : Any>() {
    private val initSize = 16
    private var stackArray = arrayOfNulls<Any>(initSize)
    private var head = -1


    fun push(item: E) {
        if (isMoreHalf()) resize(getSize() * 2)
        stackArray[++head] = item
    }

    fun pop(): E {
        checkCondition()
        return stackArray[head--] as E
    }

    fun peek(): E {
        if (isEmpty()) throw NoSuchElementException()
        return stackArray[head] as E
    }

    private fun checkCondition() {
        when {
            isEmpty() -> throw NoSuchElementException()
            !isMoreHalf() && getSize() > initSize -> resize(getSize() / 2)
        }

    }

    private fun resize(newSize: Int) {
        stackArray = stackArray.copyOf(newSize)
    }


    private fun isEmpty(): Boolean = head == -1

    private fun isMoreHalf(): Boolean = head >= getSize() / 2-2

    fun getSize() = stackArray.size
}
