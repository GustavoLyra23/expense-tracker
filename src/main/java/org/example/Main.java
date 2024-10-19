package org.example;

import org.example.factory.ExpanseFactory;
import org.example.service.IFilePersist;
import org.example.service.JSONService;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String regex = "add\\s+--description\\s+\"([^\"]+)\"\\s+--amount\\s+(\\d+(\\.\\d+)?)";
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        IFilePersist filePersist = JSONService.getInstance();
        do {
            var command = sc.nextLine();
            checkCommand(command);
            addExpense(command, regex, filePersist);
        } while (true);


    }

    private static void checkCommand(String command) {
        if (command.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
    }

    private static void addExpense(String command, String regex, IFilePersist filePersist) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        if (!matcher.matches()) {
            System.out.println("Invalid command");
        } else {
            var expanse = ExpanseFactory.createExpanse(matcher.group(1), matcher.group(2));
            String fileName = generateFileName();
            filePersist.createFile(fileName, expanse);
            System.out.println("Expense added successfully (ID: )" + expanse.getId());
        }
    }

    private static String generateFileName() {
        return "data_" + YearMonth.now();
    }
}



