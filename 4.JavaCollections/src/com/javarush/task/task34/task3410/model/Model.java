package com.javarush.task.task34.task3410.model;

import com.javarush.task.task34.task3410.controller.EventListener;

import java.nio.file.Paths;

public class Model {
    public final static int FIELD_CELL_SIZE = 20;
    private EventListener eventListener;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Paths.get("./4.JavaCollections/src/com/javarush/task/task34/task3410/res/levels.txt"));
    private GameObjects gameObjects = levelLoader.getLevel(currentLevel);

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        restart();
    }

    public void move(Direction direction) {
        if (checkWallCollision(getGameObjects().getPlayer(), direction)) return;
        if (checkBoxCollisionAndMoveIfAvailable(direction)) return;
        int[] newPlayerCoords = directionToCoords(getGameObjects().getPlayer(), direction);
        getGameObjects().getPlayer().move(newPlayerCoords[0], newPlayerCoords[1]);
        checkCompletion();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        for (Wall wall : getGameObjects().getWalls()) {
            if (gameObject.isCollision(wall, direction)) return true;
        }
        return false;
    }

    public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        Box collisionBox = null;
        for (Box box : getGameObjects().getBoxes()) {
            if (getGameObjects().getPlayer().isCollision(box, direction)) {
                collisionBox = box;
                break;
            }
        }
        if (collisionBox == null) return false;
        if (checkWallCollision(collisionBox, direction)) return true;
        for (Box box : getGameObjects().getBoxes()) {
            if (collisionBox.isCollision(box, direction)) return true;
        }
        int[] newBoxCoords = directionToCoords(collisionBox, direction);
        collisionBox.move(newBoxCoords[0], newBoxCoords[1]);
        return false;
    }

    public void checkCompletion() {
        boolean allOccupied = true;
        for (Home home : getGameObjects().getHomes()) {
            boolean homeOccupied = false;
            for (Box box : getGameObjects().getBoxes()) {
                if (home.getX() == box.getX() && home.getY() == box.getY()) {
                    homeOccupied = true;
                    break;
                }
            }
            if (!homeOccupied) {
                allOccupied = false;
                break;
            }
        }
        if (allOccupied) {
            eventListener.levelCompleted(currentLevel);
        }
    }

    private int[] directionToCoords(GameObject gameObject, Direction direction) {
        int[] result = new int[2];
        result[0] = 0;
        result[1] = 0;
        switch (direction) {
            case UP:
                result[1] -= FIELD_CELL_SIZE;
                break;
            case DOWN:
                result[1] += FIELD_CELL_SIZE;
                break;
            case LEFT:
                result[0] -= FIELD_CELL_SIZE;
                break;
            case RIGHT:
                result[0] += FIELD_CELL_SIZE;
        }
        return result;
    }
}
