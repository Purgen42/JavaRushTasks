package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Home extends GameObject {

    public Home(int x, int y) {
        super(x, y, 2, 2);
    }

    @Override
    public void draw(Graphics graphics) {
        int width = this.getWidth();
        int height = this.getHeight();
        int upperLeftX = this.getX() - width / 2;
        int upperLeftY = this.getY() - height / 2;
        graphics.setColor(Color.RED);
        graphics.drawOval(upperLeftX, upperLeftY, width, height);
    }
}
