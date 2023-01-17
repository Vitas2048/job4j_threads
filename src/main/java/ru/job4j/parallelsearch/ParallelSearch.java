package ru.job4j.parallelsearch;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;

    private final int from;

    private final int to;

    private final T search;

    public ParallelSearch(T [] array, T search, int from, int to) {
        this.array = array;
        this.search = search;
        this.to = to;
        this.from = from;
    }

    public static <T> int parallelFind(T[] array, T search) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return (int) forkJoinPool.invoke(new ParallelSearch(array, search, 0, array.length - 1));
    }

    private int linearFind() {
        for (int i = from; i <= to; i++) {
            if (array[i].equals(search)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return linearFind();
        }
        int mid = (from + to) / 2;
        ParallelSearch left = new ParallelSearch(array, search, from, mid );
        ParallelSearch right = new ParallelSearch(array, search, mid + 1, to);
        left.fork();
        right.fork();
        return Math.max((Integer) left.join(), (Integer) right.join());
    }
}