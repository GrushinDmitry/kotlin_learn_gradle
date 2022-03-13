package homework.lesson4


class MyQueue<E>(private val size: Int = 16) {
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

    //Пытается добавить оbj в очередь.
    //Возвращает true, если оbj добавлен, и false в противном случае.
    fun offer(obj: E): Boolean {
        var result = true
        if (isFull()) {
            result = false
        } else if (tail == size) {
            tail = 0
            legalOffer(obj)
        } else {
            legalOffer(obj)
        }
        return result
    }

    private fun legalOffer(_obj: E) {
        queueArray[tail++] = _obj
        items++
    }

    //Удаляет элемент из головы очереди, возвращая его.
    //Инициирует исключение NoSuchElementException, если очередь пуста.
    fun remove(): E {
        if (isEmpty()) {
            throw NoSuchElementException()
        } else if (head == size) {
            head = 0
        }
        return removeReturn()
    }

    private fun removeReturn(): E {
        val temp = queueArray[head] as E
        queueArray[head++] = null
        items--
        return temp
    }

    //Возвращает элемент из головы очереди.
    //Возвращает null, если очередь пуста. Элемент не удаляется.
    fun peek(): E? = if (isEmpty()) {
        null
    } else {
        queueArray[head] as E
    }


    //Возвращает элемент из головы очереди и удаляет его.
    // Возвращает null, если очередь пуста.
    fun poll(): E? {
        val result: E?
        if (isEmpty()) {
            result = null
        } else if (head == size) {
            head = 0
            result = removeReturn()
        } else {
            result = removeReturn()
        }
        return result


    }

    private fun isEmpty(): Boolean {
        return items == 0
    }

    private fun isFull(): Boolean {
        return items == size
    }


}