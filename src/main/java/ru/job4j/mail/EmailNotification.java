package ru.job4j.mail;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                String subject = String.format("Notification %s to email %s", user.getUserName(), user.getEmail());
                String body = String.format("Add a new event to %s", user.getUserName());
                send(subject, body, user.getEmail());
            }
        });
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {
    }
}
