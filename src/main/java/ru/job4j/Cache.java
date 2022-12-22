package ru.job4j;

public class Cache {
    private static Cache cache;

    public static synchronized Cache instanceOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
