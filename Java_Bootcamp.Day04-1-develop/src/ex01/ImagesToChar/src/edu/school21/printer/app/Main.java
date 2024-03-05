package edu.school21.printer.app;

import edu.school21.printer.logic.Controller;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Введите символы:");
        Scanner in = new Scanner(System.in);
        char a = in.next().toCharArray()[0], b = in.next().toCharArray()[0];
        Controller.paint(a, b);
        in.close();
    }
}