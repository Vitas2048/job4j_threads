package ru.job4j.parallelsearch;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelSearch extends RecursiveTask<Integer> {

    private final Object[] array;

    private final Object search;
    public ParallelSearch(Object[] array, Object search) {
        this.array = array;
        this.search = search;
    }

    @Override
    protected Integer compute() {
        if (array.length > 10) {
            ParallelSearch left = new ParallelSearch(Arrays.copyOfRange(array, 0, array.length / 2), search);
            ParallelSearch right = new ParallelSearch(Arrays.copyOfRange(array, array.length / 2, array.length), search);
            left.fork();
            right.fork();
            if (right.join() == 0 && left.join() != 0) {
               return left.join();
            }
            if (left.join() == 0 && right.join() != 0) {
                return left.join() + right.join();
            }
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(search)) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }
}
