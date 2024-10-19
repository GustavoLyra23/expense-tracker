package org.example;

import org.example.exceptions.FileException;
import org.example.exceptions.JsonException;
import org.example.factory.ExpanseFactory;
import org.example.services.IFileManager;
import org.example.services.JSONService;
import org.example.utils.PatternUtil;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String regex = "add\\s+--description\\s+\"([^\"]+)\"\\s+--amount\\s+(\\d+(\\.\\d+)?)";
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        IFileManager filePersist = JSONService.getInstance();
        String command;

        do {
            try {
                command = sc.nextLine();
                verifyExitCommand(command);
                addExpense(command, regex, filePersist);
            } catch (FileException | JsonException e) {
                System.out.println(e.getMessage());
            }
        } while (true);


    }

    private static void verifyExitCommand(String command) {
        if (command.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
    }

    private static void addExpense(String command, String regex, IFileManager filePersist) {
        var matcher = PatternUtil.regex(command, regex);
        if (!matcher.matches()) {
            System.out.println("Invalid command");
        } else {
            var expanse = ExpanseFactory.createExpanse(matcher.group(1), matcher.group(2));
            String fileName = generateFileName();
            filePersist.createFile(fileName, expanse);
            System.out.println("Expense added successfully (ID:" + expanse.getId() + ")");
        }
    }

    private static String generateFileName() {
        return "data_" + YearMonth.now();
    }
}



