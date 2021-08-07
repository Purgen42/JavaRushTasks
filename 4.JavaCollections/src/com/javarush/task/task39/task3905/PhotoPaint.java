package com.javarush.task.task39.task3905;

public class PhotoPaint {
    public boolean paintFill(Color[][] image, int x, int y, Color desiredColor) {
        if (image == null) return false;
        if (x >= image[0].length) return false;
        if (y >= image.length) return false;
        if (x < 0) return false;
        if (y < 0) return false;
        if (image[y][x] == desiredColor) return false;
        Color originalColor = image[y][x];
        image[y][x] = desiredColor;
        if (x < image[0].length - 1 && image[y][x + 1] == originalColor) paintFill(image, x + 1, y, desiredColor);
        if (x > 0 && image[y][x - 1] == originalColor) paintFill(image, x - 1, y, desiredColor);
        if (y < image.length - 1 && image[y + 1][x] == originalColor) paintFill(image, x, y + 1, desiredColor);
        if (y > 0 && image[y - 1][x] == originalColor) paintFill(image, x, y - 1, desiredColor);
        return true;
    }
}
