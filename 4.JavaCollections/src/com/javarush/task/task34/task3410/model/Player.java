package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Player extends CollisionObject implements Movable {

    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        int width = this.getWidth();
        int height = this.getHeight();
        int upperLeftX = this.getX() - width / 2;
        int upperLeftY = this.getY() - height / 2;
        graphics.setColor(Color.YELLOW);
        graphics.fillOval(upperLeftX, upperLeftY, width, height);
    }

    @Override
    public void move(int x, int y) {
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }
}
