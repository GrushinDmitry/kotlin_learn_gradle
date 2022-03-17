package homework.lesson4


class MyQueue<E : Any>(private val size: Int = 16) {
    private val queueArray = arrayOfNulls<Any>(size)
    private var head = 0
    private var tail = 0
    private var items = 0


    //Пытается добавить item в очередь.
    //Возвращает true, если item добавлен, и false в противном случае.
    fun offer(item: E): Boolean =
        when {
            isFull() -> false
            else -> offerInternal(item)
        }


    private fun offerInternal(item: E): Boolean {
        if (tail == size) tail = 0
        queueArray[tail++] = item
        items++
        return true
    }

    //Возвращает элемент из головы очереди. Элемент не удаляется.
    // Если очередь пуста, инициируется исключение NoSuchElementException.
    fun element(): E = peek() ?: throw NoSuchElementException()


    //Возвращает элемент из головы очереди.
    //Возвращает null, если очередь пуста. Элемент не удаляется.
    fun peek(): E? = if (isEmpty()) null else queueArray[head] as E


    //Удаляет элемент из головы очереди, возвращая его.
    //Инициирует исключение NoSuchElementException, если очередь пуста.
    fun remove(): E = poll() ?: throw NoSuchElementException()


    //Возвращает элемент из головы очереди и удаляет его.
    // Возвращает null, если очередь пуста.
    fun poll(): E? =
        when {
            isEmpty() -> null
            else -> pollInternal()
        }


    private fun pollInternal(): E {
        if (head == size) head = 0
        items--
        return queueArray[head++] as E
    }


    private fun isEmpty(): Boolean = items == 0


    private fun isFull(): Boolean = items == size


}