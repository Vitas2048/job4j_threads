package ru.job4j.pool;

import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public void work(Runnable job) {
        try {
            tasks.offer(job);
            if (threads.size() < size) {
                threads.add(new Thread(tasks.poll()));
            }
            threads.forEach(Thread::start);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}