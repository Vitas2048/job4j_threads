package ru.job4j.parallelsearch;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Integer> {

    private final Object[] array;

    private final Object search;
    public ParallelSearch(Object[] array, Object search) {
        this.array = array;
        this.search = search;
    }

    @Override
    protected Integer compute() {
        int res = array.length / 2;
        boolean find = false;
        if (array.length > 10) {
            ParallelSearch left = new ParallelSearch(Arrays.copyOfRange(array, 0, array.length / 2), search);
            ParallelSearch right = new ParallelSearch(Arrays.copyOfRange(array, array.length / 2, array.length), search);
            left.fork();
            right.fork();
            res += right.join();
            if (left.join() == 0 && right.join() != 0) {
                return res;
            }
            res = left.join();
            return res;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(search)) {
                res = i;
                return res;
            }
        }
        return 0;
    }
}
