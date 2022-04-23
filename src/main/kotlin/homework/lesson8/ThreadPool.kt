package homework.lesson8

import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue

class ThreadPool(size: Int) : Executor {

    private val tasks = LinkedBlockingQueue<Runnable>()
    private val threads = LinkedList<WorkerThread>()
    private val monitor=tasks as Object

    init {
        if (size !in 1..maxSize) throw IllegalArgumentException("The size = $size not in the range of 1 to $maxSize")
        repeat(size) {
            val workerThread = WorkerThread()
            threads.offer(workerThread)
            workerThread.start()
        }
    }

    override fun execute(command: Runnable) {
        synchronized(tasks) {
            tasks.offer(command)
            monitor.notify()
        }
    }

    fun shutdown() {
        threads.forEach { it.interrupt(); it.cancel() }
    }

    private inner class WorkerThread : Thread() {
        private var isRunning = true
        override fun run() {
            var task: Runnable?
            while (isRunning) {
                synchronized(tasks) {
                    if (tasks.isEmpty()) {
                        try {
                            monitor.wait()
                        } catch (e: InterruptedException) {
                            isRunning = false
                        }
                    }
                    task = tasks.poll()
                }
                task?.run()
            }
        }

        fun cancel() {
            isRunning = false
            interrupt()
        }
    }
}

private const val maxSize = 8
