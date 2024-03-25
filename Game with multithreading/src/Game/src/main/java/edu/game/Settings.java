package edu.game;

import com.diogonunes.jcdp.color.api.Ansi;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {

    private char enemy;
    private char player;
    private char wall;
    private char goal;
    private static char empty;

    private Ansi.BColor enemyColor;
    private Ansi.BColor playerColor;
    private Ansi.BColor wallColor;
    private Ansi.BColor goalColor;
    private Ansi.BColor emptyColor;
    private Ansi.FColor enemyFColor;

    Settings(String profile) {
        uploadProperties(profile);
    }

    private void uploadProperties(String profile) {
        ClassLoader classLoader = getClass().getClassLoader();
        String resourcePath;
        if (profile == null || profile.equals("production")) {
            resourcePath = "application-production.properties";
        } else if (profile.equals("example")) {
            resourcePath = "application-example.properties";
        } else {
            throw new IllegalArgumentException("Неподдерживаемый профиль: " + profile);
        }
        try (InputStream inputStream = classLoader.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("Файла с настройками для профиля " + profile + " не найдено");
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            enemy = properties.getProperty("enemy.char").charAt(0);
            player = properties.getProperty("player.char").charAt(0);
            wall = properties.getProperty("wall.char").charAt(0);
            goal = properties.getProperty("goal.char").charAt(0);
            empty = properties.getProperty("empty.char").charAt(0);
            enemyColor = Ansi.BColor.valueOf(properties.getProperty("enemy.color"));
            playerColor = Ansi.BColor.valueOf(properties.getProperty("player.color"));
            wallColor = Ansi.BColor.valueOf(properties.getProperty("wall.color"));
            goalColor = Ansi.BColor.valueOf(properties.getProperty("goal.color"));
            emptyColor = Ansi.BColor.valueOf(properties.getProperty("empty.color"));
            enemyFColor = Ansi.FColor.valueOf(properties.getProperty("empty.color"));
        } catch (IOException e) {
            System.out.println("Файла с такими настройками нет!!");
            System.exit(-1);
        }
    }

    public char getChar(Type type) {
        return switch (type) {
            case WALL -> wall;
            case GOAL -> goal;
            case PLAYER -> player;
            case EMPTY -> empty;
            case ENEMY -> enemy;
        };
    }

    public Ansi.BColor getColor(Type type) {
        return switch (type) {
            case WALL -> wallColor;
            case GOAL -> goalColor;
            case PLAYER -> playerColor;
            case EMPTY -> emptyColor;
            case ENEMY -> enemyColor;
        };
    }

    public Ansi.FColor getEnemyFColor() {
        return enemyFColor;
    }

    public Type setType(char symb) {
        if (symb == empty) return Type.EMPTY;
        if (symb == enemy) return Type.ENEMY;
        if (symb == player) return Type.PLAYER;
        if (symb == goal) return Type.GOAL;
        if (symb == wall) return Type.WALL;
        return null;
    }
}
