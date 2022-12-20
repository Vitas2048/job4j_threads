package ru.job4j.concurrent;


public class ConsoleProgress implements Runnable {

    private final char[] b =  {'/', '|', '\\', '|'};

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (char a : b) {
                    System.out.print("\r loading: " + a);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }
}
