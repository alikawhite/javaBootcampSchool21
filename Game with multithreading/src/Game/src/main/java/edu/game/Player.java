package edu.game;
public class Player {
    
    private int posX;
    private int posY;

    public Player(int[] ints) {
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
