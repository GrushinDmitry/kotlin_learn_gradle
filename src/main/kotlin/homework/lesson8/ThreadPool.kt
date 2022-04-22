package homework.lesson8

import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue

class ThreadPool(size: Int) : Executor {

    private val tasks = LinkedBlockingQueue<Runnable>()
    private val threads = LinkedList<WorkerThread>()

    init {
        if (size !in 1..maxSize) throw IllegalArgumentException(
            "The size = $size not in the range of 1 to $maxSize"
        )
        repeat(size) {
            val workerThread = WorkerThread()
            threads.offer(workerThread)
            workerThread.start()
        }
    }

    override fun execute(command: Runnable) {
        synchronized(tasks) {
            tasks.offer(command)
            (tasks as Object).notify()
        }
    }

    fun shutdown() {
        threads.forEach { it.interrupt(); it.isRunning = false }
    }

    private inner class WorkerThread : Thread() {
        @Volatile
        var isRunning = true
        private var task: Runnable? = null
        override fun run() {
            while (isRunning) {
                synchronized(tasks) {
                    if (tasks.isEmpty()) {
                        try {
                            (tasks as Object).wait()
                        } catch (e: InterruptedException) {
                            isRunning = false
                        }
                    }
                    task = tasks.poll()
                }
                if (task != null) task!!.run()
            }
        }
    }
}

private const val maxSize = 8
