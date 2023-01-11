package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i] = new Sums();
            sums[i].setRowSum(rowSum);
            sums[i].setColSum(colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
       Sums[] sums = new Sums[matrix.length];
       int rowSum = 0;
       int colSum = 0;
       for (int i = 0; i < matrix.length; i++) {
           sums[i] = new Sums();
           sums[i].setRowSum(getRow(matrix, i, matrix.length).get());
           sums[i].setColSum(getColumn(matrix, i, matrix.length).get());
       }
        return sums;
    }

    public static CompletableFuture<Integer> getRow(int[][] data, int current, int matrixLength) {
        return CompletableFuture.supplyAsync(() -> {
           int rowSum = 0;
           for (int i = 0; i < matrixLength; i++) {
               rowSum += data[current][i];
           }
           return rowSum;
        });
    }

    public static CompletableFuture<Integer> getColumn(int[][] data, int current, int matrixLength) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            for (int i = 0; i < matrixLength; i++) {
                rowSum += data[i][current];
            }
            return rowSum;
        });
    }

}