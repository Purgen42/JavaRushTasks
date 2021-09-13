package com.javarush.task.task34.task3410.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.javarush.task.task34.task3410.model.Model.FIELD_CELL_SIZE;

public class LevelLoader {
    private Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        List<String> allLines = null;
        try {
            allLines = Files.readAllLines(levels);
        } catch (IOException e) {
            e.printStackTrace();
        }

        level = (level - 1) % 60 + 1;

        int startIndex = allLines.indexOf(String.format("Maze: %d", level));
        int sizeX = Integer.parseInt(allLines.get(startIndex + 2).substring(8));
        int sizeY = Integer.parseInt(allLines.get(startIndex + 3).substring(8));

        List<String> mazeLines = allLines.subList(startIndex + 7, startIndex + 7 + sizeY);

        Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Player player = null;

        for (int i = 0; i < mazeLines.size(); i++) {
            String line = mazeLines.get(i);
            for (int j = 0; j < line.length(); j++) {
                int objX = FIELD_CELL_SIZE / 2 + j * FIELD_CELL_SIZE;
                int objY = FIELD_CELL_SIZE / 2 + i * FIELD_CELL_SIZE;
                char obj = line.charAt(j);
                switch (obj) {
                    case 'X':
                        walls.add(new Wall(objX, objY));
                        break;
                    case '*':
                        boxes.add(new Box(objX, objY));
                        break;
                    case '.':
                        homes.add(new Home(objX, objY));
                        break;
                    case '&':
                        boxes.add(new Box(objX, objY));
                        homes.add(new Home(objX, objY));
                        break;
                    case '@':
                        player = new Player(objX, objY);
                }
            }
        }

        return new GameObjects(walls, boxes, homes, player);
    }
}
