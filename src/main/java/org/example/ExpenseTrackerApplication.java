package org.example;

import org.example.exceptions.FileException;
import org.example.exceptions.JsonException;
import org.example.factory.ExpanseFactory;
import org.example.services.ExpenseService;
import org.example.services.IFileManager;
import org.example.services.JSONService;
import org.example.utils.PatternUtil;

import java.time.YearMonth;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ExpenseTrackerApplication {


    public static void main(String[] args) {
        String regex = "add\\s+--description\\s+\"([^\"]+)\"\\s+--amount\\s+(\\d+(\\.\\d+)?)";
        Scanner sc = new Scanner(System.in);
        IFileManager filePersist = JSONService.getInstance();
        ExpenseService.initialize(filePersist);
        ExpenseService expenseService = ExpenseService.getInstance();
        String command;

        do {
            try {
                command = sc.nextLine();
                String filePath = System.getProperty("user.home") + "/myApp/" + generateFileName();
                verifyCommand(command, expenseService, filePath, regex, filePersist);
            } catch (FileException | JsonException e) {
                System.out.println(e.getMessage());
            }
        } while (true);


    }

    private static void verifyCommand(String command, ExpenseService expenseService, String filePath, String regex, IFileManager filePersist) {
        switch (command) {
            case "list":
                expenseService.findAll(filePath);
                break;
            case "exit":
                System.exit(0);
                break;
            case "summary":
                System.out.println("Total expenses: $" + expenseService.amountTotal(filePath));
                break;
            default:
                if (PatternUtil.regexMatches(command, regex)) {
                    addExpense(command, regex, filePersist, filePath);
                } else {
                    System.out.println("Invalid command");
                }
        }
    }

    private static void addExpense(String command, String regex, IFileManager filePersist, String filePath) {
        Matcher matcher = PatternUtil.regex(command, regex);
        if (matcher.matches()) {
            var expanse = ExpanseFactory.createExpanse(matcher.group(1), matcher.group(2));
            var id = filePersist.createFile(expanse, filePath);
            System.out.println("Expense added successfully (ID:" + id + ")");
        } else {
            System.out.println("Invalid command");
        }
    }

    private static String generateFileName() {
        return "data_" + YearMonth.now();
    }

}



