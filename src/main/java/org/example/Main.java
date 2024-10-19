package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            var command = sc.nextLine();
            checkCommand(command);
        }


    }

    private static void checkCommand(String command) {
        if (command.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
    }


}