package homework.lesson4


class MyQueue<E : Any>(private val size: Int = 16) {
    private val queueArray = arrayOfNulls<Any>(size)
    private var head = 0
    private var tail = 0
    private var items = 0

    //Возвращает элемент из головы очереди. Элемент не удаляется.
    // Если очередь пуста, инициируется исключение NoSuchElementException.
    fun element(): E {
        if (isEmpty()) {
            throw NoSuchElementException()
        }
        return queueArray[head] as E
    }

    //Пытается добавить item в очередь.
    //Возвращает true, если item добавлен, и false в противном случае.
    fun offer(item: E): Boolean =
        when {
            isFull() -> false
            tail == size -> {
                tail = 0
                offerInternal(item)
            }
            else -> {
                offerInternal(item)
            }
        }


    private fun offerInternal(itemLegal: E): Boolean {
        queueArray[tail++] = itemLegal
        items++
        return true
    }

    //Удаляет элемент из головы очереди, возвращая его.
    //Инициирует исключение NoSuchElementException, если очередь пуста.
    fun remove(): E =
        when {
            isEmpty() -> throw NoSuchElementException()
            else -> poll()!!
        }


    private fun removeInternal(): E {
        val element = queueArray[head++] as E
        items--
        return element
    }

    //Возвращает элемент из головы очереди.
    //Возвращает null, если очередь пуста. Элемент не удаляется.
    fun peek(): E? = if (isEmpty()) null else queueArray[head] as E


    //Возвращает элемент из головы очереди и удаляет его.
    // Возвращает null, если очередь пуста.
    fun poll(): E? =
        when {
            isEmpty() -> null
            head == size -> {
                head = 0
                removeInternal()
            }
            else -> removeInternal()
        }


    private fun isEmpty(): Boolean = items == 0


    private fun isFull(): Boolean = items == size


}