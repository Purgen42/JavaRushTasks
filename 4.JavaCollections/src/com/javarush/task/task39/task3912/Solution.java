package com.javarush.task.task39.task3912;

/* 
Максимальная площадь
*/

public class Solution {
    public static void main(String[] args) {
        int testArray[][] = {{0, 0, 0, 0, 0},
                             {0, 0, 0, 0, 0},
                             {0, 1, 1, 1, 0},
                             {0, 1, 1, 1, 0},
                             {0, 1, 1, 1, 0}};
        System.out.println(maxSquare(testArray));
    }

    public static int maxSquare(int[][] matrix) {
        int maxSide = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int side = maxSideAtCoords(matrix, j, i);
                if (side > maxSide) maxSide = side;
            }
        }
        return maxSide * maxSide;
    }

    private static int maxSideAtCoords(int[][] matrix, int x, int y) {
        int maxX = matrix[0].length - 1;
        int maxY = matrix.length - 1;
        int offset = 0;
loop:   while (true) {
            offset++;
            if (x + offset > maxX) break;
            if (y + offset > maxY) break;
            for (int i = 0; i <= offset; i++) {
                if (matrix[y + offset][x + i] == 0) break loop;
                if (matrix[y + i][x + offset] == 0) break loop;
            }
        }
        return offset;
    }
}
