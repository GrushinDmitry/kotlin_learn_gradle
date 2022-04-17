package homework.lesson8

import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue

class ThreadPool(size: Int) : Executor {
    @Volatile
    private var isRunning = true
    private val tasks = LinkedBlockingQueue<Runnable>()
    private val threads = LinkedList<WorkerThread>()

    init {
        if (threads.size !in 1..maxSize) throw IllegalArgumentException("The size not in the range of 1 to $maxSize")
        repeat(size) {
            val workerThread = WorkerThread()
            workerThread.start()
            threads.add(workerThread)
        }
    }

    override fun execute(command: Runnable) {
        if (!isRunning) throw IllegalThreadStateException("Thread pool stopped")
        synchronized(tasks) {
            tasks.add(command)
            (tasks as Object).notifyAll()
        }
    }

    fun shutdown() {
        threads.forEach { it.interrupt() }
        isRunning = false
    }

    private inner class WorkerThread : Thread() {
        lateinit var task: Runnable
        override fun run() {
            while (true) {
                synchronized(tasks) {
                    while (tasks.isEmpty() && isRunning) {
                        try {
                            (tasks as Object).wait()
                        } catch (e: InterruptedException) {
                            println("An error occurred while tasks is waiting: " + e.message)
                        }
                    }
                    task.run()
                    try {
                        task = tasks.poll()
                        task.run()
                        (tasks as Object).notifyAll()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}

private const val maxSize = 8


