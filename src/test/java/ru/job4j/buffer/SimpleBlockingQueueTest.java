package ru.job4j.buffer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void whenMoreThan1ThenPolled13() throws InterruptedException {
        var simpleBlockingQueue = new SimpleBlockingQueue();
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
                    simpleBlockingQueue.offer(12);
                    System.out.println("offered 12");
                    simpleBlockingQueue.offer(13);
                    System.out.println("offered 13");

                }, "OFFER"
        );
        offer.start();
        poll.start();
        offer.join();
        poll.join();
        assertEquals(13, simpleBlockingQueue.poll());
    }
}