package edu.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;

public class Logic {
    private Map map;

    public Logic(Map map) {
        this.map = map;
    }

    public void makeLogic() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String action;
        Enemy[] enemies = map.setEnemies();

        try {
            Painter.paintMap(map.getMap(), map.getSettings());
            while (true) {
                System.out.println("Введите команду: ");
                action = reader.readLine();
                if (action.length() != 1)
                    continue;
                if (action.charAt(0) == '9')
                    break;
                if (action.charAt(0) == '\n')
                    continue;
                if (checkMove(action.charAt(0))) {
                    Painter.paintMap(map.getMap(), map.getSettings());
                    System.out.println("Введите 8 для хода противника, 9 – выйти:");
                    while (true) {
                        String ch = reader.readLine();
                        if (ch.length() != 1)
                            continue;
                        if (ch.charAt(0) == '8')
                            break;
                        if (ch.charAt(0) == '9') {
                            reader.close();
                            System.exit(0);
                        }
                    }
                    Thread[] threads = new Thread[map.getEnemiesCount()];
                    for (int i = 0; i < threads.length; ++i) {
                        threads[i] = new EnemyWay(enemies[i], map);
                        threads[i].start();
                    }
                    sleep(500);
                    Painter.paintMap(map.getMap(), map.getSettings());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public boolean checkMove(char action) {
        return switch (action) {
            case 'W', 'w' -> playersMove(0, -1);
            case 'A', 'a' -> playersMove(-1, 0);
            case 'S', 's' -> playersMove(0, 1);
            case 'D', 'd' -> playersMove(1, 0);
            default -> false;
        };
    }

    public boolean playersMove(int x, int y) {
        int posX = map.getPlayer().getPosX(), posY = map.getPlayer().getPosY();
        if (posX + x >= map.getSize() || posY >= map.getSize()
                || posY + y < 0 || posX + x < 0) return false;
        Type type = map.getMapsPos(posX + x, posY + y);
        if (type == Type.ENEMY) {
            System.out.println("О, нет, вы натолкнулись на врага. Мгновенная смерть!!");
            System.exit(0);
        }
        if (type == Type.WALL) {
            System.out.println("Вы хотите поцеловать стену? Давайте может еще раз");
            return false;
        }
        if (type == Type.GOAL) {
            System.out.println("Это победа!");
            System.exit(0);
        }
        map.setMapsPos(posX, posY, Type.EMPTY);
        map.setMapsPos(posX + x, posY + y, Type.PLAYER);
        map.getPlayer().changeX(posX + x);
        map.getPlayer().changeY(posY + y);
        return true;
    }
}
