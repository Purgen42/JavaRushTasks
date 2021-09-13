package com.javarush.task.task34.task3410.model;

public abstract class CollisionObject extends GameObject {
    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        int objectX = gameObject.getX();
        int objectY = gameObject.getY();
        int thisX = this.getX();
        int thisY = this.getY();
        switch (direction) {
            case UP:
                thisY -= Model.FIELD_CELL_SIZE;
                break;
            case DOWN:
                thisY += Model.FIELD_CELL_SIZE;
                break;
            case LEFT:
                thisX -= Model.FIELD_CELL_SIZE;
                break;
            case RIGHT:
                thisX += Model.FIELD_CELL_SIZE;
                break;
        }
        return (thisX == objectX && thisY == objectY);
    }
}
