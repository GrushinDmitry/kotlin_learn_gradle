package homework.lesson8

import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue

class ThreadPool(size: Int) : Executor {

    private val tasks = LinkedBlockingQueue<Runnable>()
    private val threads = LinkedList<WorkerThread>()

    @Volatile
    private var isStarted = false

    init {
        if (size !in 1..maxSize) throw IllegalArgumentException("The size not in the range of 1 to $maxSize")
        threads.forEach { it.start() }
    }

    override fun execute(command: Runnable) {
        if (!isStarted) throw IllegalThreadStateException("Thread pool stopped")
        synchronized(tasks) {
            tasks.add(command)
            (tasks as Object).notify()
        }
    }

    fun shutdown() {
        isStarted = false
        synchronized(tasks) {
            (tasks as Object).notifyAll()
        }
    }

    private class WorkerThread : Thread() {

        override fun run() {

            while (true) {

            }
        }

    }

}

private const val maxSize = 8