package ru.job4j.buffer;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class SimpleBlockingQueueTest {
    @Test
    public void whenMoreThan1ThenPolled13() throws InterruptedException {
        var simpleBlockingQueue = new SimpleBlockingQueue(1);
        Thread poll = new Thread(
                () -> {
                    try {
                        System.out.println("polled " + simpleBlockingQueue.poll());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        Thread offer = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.offer(12);
                        System.out.println("offered 12");
                        simpleBlockingQueue.offer(13);
                        System.out.println("offered 13");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, "OFFER"
        );
        offer.start();
        poll.start();
        offer.join();
        poll.join();
        assertEquals(13, simpleBlockingQueue.poll());
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(p -> {
                        try {
                            queue.offer(p);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void blockingTest() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(7);
        Thread offer = new Thread(
                () -> {
                    IntStream.range(0, 9).forEach(p -> {
                                try {
                                    queue.offer(p);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    );
                }
        );
        offer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        offer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8)));
    }
}