package ru.job4j.thread.threadlocal;

public class ThreadLocalDemo {
    private static ThreadLocal<String> tl = new ThreadLocal<>();

    public static class FirstThread extends Thread {
        @Override
        public void run() {
            ThreadLocalDemo.tl.set("Это поток 1");
            System.out.println(ThreadLocalDemo.tl.get());
        }
    }

    public static class SecondThread extends Thread {
        @Override
        public void run() {
            ThreadLocalDemo.tl.set("Это поток 2");
            System.out.println(ThreadLocalDemo.tl.get());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread first = new FirstThread();
        Thread second = new SecondThread();
        tl.set("Это поток main");
        System.out.println(tl.get());
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
