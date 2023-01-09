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
        ParallelSearch search = new ParallelSearch(users, user2);
        assertEquals(1, search.compute());
    }
    @Test
    public void whenStringThen2() {
        String s = "Happy";
        String s1 = "New";
        String s2 = "Year";
        String s3 = "Holidays";
        String s4 = "!!!!";
        String[] congrats = {s, s2, s3, s1, s4};
        ParallelSearch search = new ParallelSearch(congrats, "Holidays");
        assertEquals(2, search.compute());
    }

    @Test
    public void whenParallelSearchThen17() {
        String[] numbers = new String[110];
        for (int i = 0; i < 110; i++) {
            numbers[i] = String.valueOf(i);
        }
        ParallelSearch search = new ParallelSearch(numbers, "105");
        assertEquals(105, search.compute());
    }

    @Test
    public void whenParallelSearchThen22() throws NoSuchElementException {
        String[] numbers = new String[24];
        for (int i = 0; i < 24; i++) {
            numbers[i] = String.valueOf(i);
        }
        ParallelSearch search = new ParallelSearch(numbers, "1asdwd");
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                search::compute);
    }
}