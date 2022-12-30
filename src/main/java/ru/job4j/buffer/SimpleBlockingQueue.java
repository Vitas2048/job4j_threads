package ru.job4j.buffer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final int qSize;

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int qSize) {
        this.qSize = qSize;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (qSize == queue.size()) {
            this.wait();
        }
        queue.offer(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            this.wait();
        }
        T rsl = queue.poll();
        notifyAll();
        return rsl;
    }

    public synchronized boolean isEmpty() {
        return queue.size() == 0;
    }
}