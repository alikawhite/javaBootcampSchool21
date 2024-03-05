package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import edu.school21.printer.logic.Args;
import edu.school21.printer.logic.Image;

public class Main {
    public static void main(String[] args) {
        Args arguments = new Args();
        JCommander.newBuilder().addObject(arguments).build().parse(args);
        Image.showImage(arguments.getParam1(), arguments.getParam2());
    }
}