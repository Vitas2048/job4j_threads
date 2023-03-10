package ru.job4j;

public class CountShareMain {
    public static class Count {
        private int value;

        public synchronized void increment() {
            value++;
        }

        public synchronized int getValue() {
            return value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Count count = new Count();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(count.getValue());
    }
}
