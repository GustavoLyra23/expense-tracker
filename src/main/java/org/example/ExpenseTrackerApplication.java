package org.example;

import org.example.exceptions.FileException;
import org.example.exceptions.JsonException;
import org.example.factory.ExpanseFactory;
import org.example.services.ExpenseService;
import org.example.services.IFileManager;
import org.example.services.JSONService;
import org.example.utils.PatternUtil;

import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ExpenseTrackerApplication {


    public static void main(String[] args) {
        List<String> regexList = List.of("add\\s+--description\\s+\"([^\"]+)\"\\s+--amount\\s+(\\d+(\\.\\d+)?)",
                "delete\\s+--id\\s+(\\d+)", "summary\\s+--month\\s+(1[0-2]|0?[1-9])");


        Scanner sc = new Scanner(System.in);
        IFileManager filePersist = JSONService.getInstance();
        ExpenseService.initialize(filePersist);
        ExpenseService expenseService = ExpenseService.getInstance();
        String command;

        do {
            try {
                command = sc.nextLine();
                String filePath = System.getProperty("user.home") + "/myApp/" + generateFileName();
                verifyCommand(command, expenseService, filePath, regexList, filePersist);
            } catch (FileException | JsonException e) {
                System.out.println(e.getMessage());
            }
        } while (true);


    }

    private static void verifyCommand(String command, ExpenseService expenseService, String filePath, List<String> regexList, IFileManager filePersist) {
        command = command.toLowerCase();
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
                if (PatternUtil.regexMatches(command, regexList.getFirst())) {
                    addExpense(command, regexList.getFirst(), filePersist, filePath);
                } else if (PatternUtil.regexMatches(command, regexList.get(1))) {
                    Matcher matcher = PatternUtil.regex(command, regexList.get(1));
                    if (matcher.matches()) {
                        expenseService.deleteExpense(Long.parseLong(matcher.group(1)), filePath);
                    }
                } else if (PatternUtil.regexMatches(command, regexList.get(2))) {
                    Matcher matcher = PatternUtil.regex(command, regexList.get(2));
                    if (matcher.matches()) {
                        var total = expenseService.amountTotal(filePath, Integer.parseInt(matcher.group(1)));
                        System.out.println("Total expenses: $" + total);
                    }
                } else {
                    System.out.println("Invalid command");
                }
        }
    }

    private static void addExpense(String command, String regex, IFileManager filePersist, String filePath) {
        Matcher matcher = PatternUtil.regex(command, regex);
        if (matcher.matches()) {
            var expanse = ExpanseFactory.createExpanse(matcher.group(1), matcher.group(2));
            var id = filePersist.addExpense(expanse, filePath);
            System.out.println("Expense added successfully (ID:" + id + ")");
        } else {
            System.out.println("Invalid command");
        }
    }

    private static String generateFileName() {
        return "data_" + YearMonth.now();
    }

}



