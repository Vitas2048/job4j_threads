package ru.job4j.parallelsearch;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.job4j.mail.User;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ParallelSearchTest {

    @Test
    public void whenUserTypeThen1 () {
        User user1 = new User("semen", "Mail");
        User user2 = new User("Vlayd", "Ya");
        User user3 = new User("Nik", "rambler");
        User[] users = {user1, user2, user3};
        assertEquals(1, ParallelSearch.parallelFind(users, user2));
    }
    @Test
    public void whenStringThen2() {
        String s = "Happy";
        String s1 = "New";
        String s2 = "Year";
        String s3 = "Holidays";
        String s4 = "!!!!";
        String[] congrats = {s, s2, s3, s1, s4};
        assertEquals(2, ParallelSearch.parallelFind(congrats, "Holidays"));
    }

    @Test
    public void whenParallelSearchThen17() {
        String[] numbers = new String[110];
        for (int i = 0; i < 110; i++) {
            numbers[i] = String.valueOf(i);
        }
        assertEquals(17, ParallelSearch.parallelFind(numbers, "17"));
    }
    @Test
    public void whenParallelSearchThen18() {
        String[] numbers = new String[110];
        for (int i = 0; i < 110; i++) {
            numbers[i] = String.valueOf(i);
        }
        assertEquals(18, ParallelSearch.parallelFind(numbers, "18"));
    }

    @Test
    public void whenParallelSearchThen22() throws NoSuchElementException {
        String[] numbers = new String[24];
        for (int i = 0; i < 24; i++) {
            numbers[i] = String.valueOf(i);
        }
        assertEquals(-1, ParallelSearch.parallelFind(numbers, 97));
    }
}