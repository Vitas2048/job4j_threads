package ru.job4j.cache;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Test
    public void whenDifferentVerThenThrow() throws OptimisticException {
        Cache cache = new Cache();
        cache.add(new Base(1, 2));
        cache.add(new Base(2, 2));
        cache.add(new Base(3, 2));

        OptimisticException thrown = assertThrows(
                OptimisticException.class,
                () -> cache.update(new Base(2, 3)),
                "Versions are different"
        );
        assertTrue(thrown.getMessage().contains("Versions are different"));
    }

    @Test
    public void whenAddThenFalse() {
        Cache cache = new Cache();
        cache.add(new Base(1, 2));
        cache.add(new Base(2, 2));
        cache.add(new Base(3, 2));
        assertFalse(cache.add(new Base(1, 2)));
    }

    @Test
    public void whenAddAndDeleteAndAddThenTrue() {
        Cache cache = new Cache();
        cache.add(new Base(1, 2));
        cache.delete(new Base(1, 2));
        cache.add(new Base(2, 2));
        cache.add(new Base(3, 2));
        assertTrue(cache.add(new Base(1, 2)));
    }

}