package org.example;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String regex = "add\\s+--description\\s+\"([^\"]+)\"\\s+--amount\\s+(\\d+(\\.\\d+)?)";
        Scanner sc = new Scanner(System.in);

        do {
            var command = sc.nextLine();
            checkCommand(command);
            addExpense(command, regex);

        } while (true);


    }

    private static void checkCommand(String command) {
        if (command.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
    }

    private static void addExpense(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        if (!matcher.matches()) {
            System.out.println("Invalid command");
        } else {
            System.out.println("added");
        }
    }

    private static void createExpanseJson(){

    }





}