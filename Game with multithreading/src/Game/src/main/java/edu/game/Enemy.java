package edu.game;
public class Enemy {
    private int posX;
    private int posY;

    public Enemy (int[] ints) {
        posX = ints[1];
        posY = ints[0];
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void changeX(int x) {
        this.posX = x;
    }
    public void changeY(int y) {
        this.posY = y;
    }

}
