package edu.game;
import com.beust.jcommander.JCommander;

public class Main {
    public static void main(String[] args) {
        Args arguments = new Args();
        JCommander.newBuilder().addObject(arguments).build().parse(args);
        Map map = new Map(arguments.getSize(), arguments.getWallsCount(), arguments.getEnemiesCount(), arguments.getProfile());
        Logic logic = new Logic(map);
        logic.makeLogic();
    }
}
