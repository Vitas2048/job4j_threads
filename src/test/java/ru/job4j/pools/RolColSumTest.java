package ru.job4j.pools;

import org.assertj.core.internal.Arrays;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class RolColSumTest {

    @Test
    public void Column3Then40() {
        int[][] matrex = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        RolColSum.Sums[] sums = RolColSum.sum(matrex);
        assertEquals(sums[3].getColSum(), 40);
    }
    @Test
    public void AsyncColumn1Then40() throws ExecutionException, InterruptedException {
        int[][] matrex = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrex);
        assertEquals(sums[1].getColSum(), 32);
    }

    @Test
    public void AsyncRow2Then40() throws ExecutionException, InterruptedException {
        int[][] matrex = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrex);
        assertEquals(sums[2].getRowSum(), 42);
    }

}