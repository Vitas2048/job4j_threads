package ru.job4j.parallelsearch;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Integer> {

    private final Object[] array;

    private final int from;

    private final int to;

    private final Object search;

    public ParallelSearch(Object[] array, Object search, int from, int to) {
        this.array = array;
        this.search = search;
        this.to = to;
        this.from = from;
    }

    @Override
    protected Integer compute() {
        if (to - from > 10) {
            ParallelSearch left = new ParallelSearch(array, search, from, to / 2);
            ParallelSearch right = new ParallelSearch(array, search, to / 2, to - 1);
            left.fork();
            right.fork();
            if (left.join() != 0) {
                return left.join();
            }
            if (right.join() != 0) {
                return right.join();
            }
            return 0;
        }
        for (int i = from; i < to; i++) {
            if (array[i].equals(search)) {
                return i + 1;
            }
        }
        return 0;
    }
}
