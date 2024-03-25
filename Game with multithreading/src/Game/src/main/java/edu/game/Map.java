package edu.game;

import java.util.Arrays;
import java.util.Random;

public class Map {

    private final int size;
    private int enemiesCount = 0;
    private final int wallsCount;

    private final int[] goalPos;

    private char[][] map;

    private Settings settings;

    private Player player;

    public Map(int size, int wallsCount, int enemiesCount, String profile) {
        if (size <= 0 || enemiesCount < 0 || wallsCount < 0 ||
                (size * size) < (wallsCount + enemiesCount + 2)) {
            throw new IllegalParametersException("Некорректный ввод");
        }
        this.enemiesCount = enemiesCount;
        this.size = size;
        this.wallsCount = wallsCount;
        settings = new Settings(profile);
        generateMap();
        player = new Player(generateObj(Type.PLAYER));
        goalPos = generateObj(Type.GOAL);
    }

    public Enemy[] setEnemies() {
        Enemy[] enemies = new Enemy[enemiesCount];
        for (int i = 0; i < enemiesCount; ++i)
            enemies[i] = new Enemy(generateObj(Type.ENEMY));
        return enemies;
    }

    private int[] generateObj(Type who) {
        int[] result = new int[2];
        Random random = new Random();
        result[0] = random.nextInt(size); // y
        result[1] = random.nextInt(size); // x
        if (map[result[0]][result[1]] == settings.getChar(Type.EMPTY)) {
            map[result[0]][result[1]] = settings.getChar(who);
        } else {
            result = generateObj(who);
        }
        return result;
    }

    private void generateMap() {
        map = new char[size][size];
        for (int i = 0; i < size; ++i) Arrays.fill(map[i], settings.getChar(Type.EMPTY));
        generateWalls();
    }

    private void generateWalls() {
        Random random = new Random();
        int x = random.nextInt(size), y = random.nextInt(size);
        for (int i = 0; i < wallsCount; i++) {
            while (map[y][x] != settings.getChar(Type.EMPTY)) {
                x = random.nextInt(size);
                y = random.nextInt(size);
            }
            map[y][x] = settings.getChar(Type.WALL);
        }
    }

    public int getEnemiesCount() {
        return enemiesCount;
    }


    public char[][] getMap() {
        return map;
    }

    public synchronized void setMapsPos(int x, int y, Type type) {
        map[y][x] = settings.getChar(type);
    }

    public synchronized Type getMapsPos(int x, int y) {
        return settings.setType(map[y][x]);
    }

    public Player getPlayer() {
        return player;
    }

    public int getSize() {
        return size;
    }

    public Settings getSettings() {
        return settings;
    }

    public int[] getGoalPos() {
        return goalPos;
    }
}
