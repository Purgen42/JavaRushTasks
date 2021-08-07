package com.javarush.task.task39.task3905;

import java.util.Arrays;
import java.util.Random;

/* 
Залей меня полностью
*/

public class Solution {
    public static void main(String[] args) {
        Color testArray[][] = {{Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE},
                               {Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE},
                               {Color.BLUE, Color.RED,  Color.RED,  Color.RED,  Color.BLUE},
                               {Color.BLUE, Color.RED,  Color.RED,  Color.RED,  Color.BLUE},
                               {Color.BLUE, Color.RED,  Color.RED,  Color.RED,  Color.BLUE}};

        PhotoPaint paint = new PhotoPaint();
        System.out.println(Arrays.deepToString(testArray));
        paint.paintFill(testArray, 2, 3, Color.GREEN);
        System.out.println(Arrays.deepToString(testArray));
    }
}
