package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    private final String resultFile;

    public Wget(String url, int speed, String resultFile) {
        this.url = url;
        this.speed = speed;
        this.resultFile = resultFile;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(resultFile)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long current;
            long start = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    if ((current = System.nanoTime() - start) < 1e+9) {
                        Thread.sleep((long) (1000 - current * 1e-6));
                    }
                    downloadData = 0;
                    start = System.nanoTime();
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void validate(String[] arguments) {
        if (arguments.length < 1) {
            throw new NoSuchElementException("Введите аргументы");
        }
        if (Integer.parseInt(arguments[1]) <= 0) {
            throw new IllegalArgumentException("Скорость должна быть больше 0");
        }
        if (!arguments[0].isEmpty() && !arguments[0].startsWith("https://")) {
            throw new IllegalArgumentException("Введите URL");
        }
        if (!arguments[2].endsWith(".txt") && !arguments[2].endsWith(".xml")) {
            throw new IllegalArgumentException("Введите имя файла");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        String resultFile = args[2];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed, resultFile));
        wget.start();
        wget.join();
    }
}