package edu.school21.printer.logic;public class Controller {    public static void paint(String pathToFile, char a, char b) {        Painter.printMass(Parser.parseFile(pathToFile), a, b);    }}