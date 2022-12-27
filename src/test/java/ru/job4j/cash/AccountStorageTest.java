package ru.job4j.cash;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AccountStorageTest {

    @Test
    void whenAdd() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
    }

    @Test
    void whenUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        System.out.println(storage.getById(1).isEmpty());
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    void whenTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(200);
    }

    @Test
    void when2Threads() throws InterruptedException {
        var stoarge = new AccountStorage();
        stoarge.add(new Account(1, 500));
        stoarge.add(new Account(2, 800));
        stoarge.add(new Account(3, 1000));
        Thread thread1 = new Thread(
                () -> {
                    stoarge.transfer(1,2, 200);
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    stoarge.transfer(3, 1, 200);
        }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        var firstAcc = stoarge.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAcc = stoarge.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 2"));
        var thirdAcc = stoarge.getById(3)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 3"));
        assertThat(firstAcc.amount()).isEqualTo(500);
        assertThat(secondAcc.amount()).isEqualTo(1000);
        assertThat(thirdAcc.amount()).isEqualTo(800);
    }

    @Test
    void when3Threads() throws InterruptedException {
        var stoarge = new AccountStorage();
        stoarge.add(new Account(1, 500));
        stoarge.add(new Account(2, 800));
        stoarge.add(new Account(3, 1000));
        Thread thread1 = new Thread(
                () -> {
                    stoarge.transfer(1,2, 200);
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    stoarge.transfer(3, 1, 200);
                }
        );
        Thread thread3 = new Thread(
                () -> {
                    stoarge.transfer(1,2, 400);
                }
        );
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        var firstAcc = stoarge.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAcc = stoarge.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 2"));
        var thirdAcc = stoarge.getById(3)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 3"));
        assertThat(firstAcc.amount()).isEqualTo(100);
        assertThat(secondAcc.amount()).isEqualTo(1400);
        assertThat(thirdAcc.amount()).isEqualTo(800);
    }
}