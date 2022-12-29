package ru.job4j.threads;

import java.util.concurrent.Semaphore;

public class RaceConditionExample {
    private int num = 0;

    public synchronized void incr() {
        for (int i = 0; i < 99999; i++) {
            int current = num;
            int next = num + 1;
            if (current + 1 != next) {
                throw new IllegalStateException("Некорректное сравнение: " + current + " + 1 = " + next);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Semaphore sem = new Semaphore(0);
        Runnable task = () -> {
            try {
                sem.acquire();
                System.out.println("Нить выполнила задачу");
                sem.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
        Thread.sleep(3000);
        sem.release(1);
    }
}