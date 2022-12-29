package ru.job4j.blockingqueue;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.Thread.State;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void when12and13Then13() throws InterruptedException {
        var simpleBlockingQueue = new SimpleBlockingQueue();
        Thread offer = new Thread(
                () -> {
                    System.out.println("offered " + simpleBlockingQueue.poll());
                }
        );
        Thread poll = new Thread(
                () -> {
                    simpleBlockingQueue.offer(12);
                    System.out.println("polled 12");
                    simpleBlockingQueue.offer(13);
                    System.out.println("polled 13");
                }, "POLL"
        );
        offer.start();
        poll.start();
        poll.join();
        offer.join();
        assertEquals(13, simpleBlockingQueue.poll());
    }
}