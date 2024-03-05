package edu.school21.printer.app;

import edu.school21.printer.logic.Controller;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите символы:");
        Scanner in = new Scanner(System.in);
        char a = in.next().toCharArray()[0], b = in.next().toCharArray()[0];
        System.out.println("Введите полный путь до файла, который нужно " + "отрисовать, или напечатайте \"1\", чтобы отрисовался стандартный файл");
        String pathToFile = in.next();
        Controller.paint(pathToFile, a, b);
        in.close();
    }
}