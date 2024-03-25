package edu.game;

public class EnemyWay extends Thread {
    private int[] goalPosition;
    private Enemy enemy;
    private Map map;

    public EnemyWay(Enemy enemy, Map map) {
        this.enemy = enemy;
        this.map = map;
    }

    @Override
    public void run() {
        synchronized (map) {
            int[] goalPosition = findDirection(enemy.getPosX() - map.getGoalPos()[1], enemy.getPosY() - map.getGoalPos()[0]);
            moveEnemy(goalPosition);
        }
    }

    private int[] findDirection(int x, int y) {
        goalPosition = new int[2];
        if (x > 0) goalPosition[0] = -1; // left
        else if (x < 0) goalPosition[0] = 1; // right
        if (y > 0) goalPosition[1] = -1; // down
        else if (y < 0) goalPosition[1] = 1; // up
        return goalPosition;
    }

    private void moveEnemy(int[] direction) {
        try {
            int newX = enemy.getPosX(), newY = enemy.getPosY();
            if (direction[0] != 0 &&
                    map.getMapsPos(enemy.getPosX()+direction[0], enemy.getPosY()) == Type.EMPTY)
                newX += direction[0];
            else newY += direction[1];

            Type currentPosType = map.getMapsPos(newX, newY);
            if (currentPosType == Type.GOAL || currentPosType == Type.PLAYER) {
                System.out.println("Противник выиграл!!");
                System.exit(0);
            } else if (currentPosType == Type.EMPTY) {
                map.setMapsPos(newX, newY, Type.ENEMY);
                map.setMapsPos(enemy.getPosX(), enemy.getPosY(), Type.EMPTY);
                enemy.changeX(newX);
                enemy.changeY(newY);
            }
        } catch (Exception e) {
            System.out.println("Ошибка при движении противника: " + e.getMessage());
        }
    }
}
