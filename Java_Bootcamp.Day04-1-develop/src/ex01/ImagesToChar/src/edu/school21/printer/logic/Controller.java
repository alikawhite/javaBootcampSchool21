package edu.school21.printer.logic;

public class Controller {
    public static void paint(char a, char b) {
        Painter.printMass(Parser.parseFile(), a, b);
    }
}
