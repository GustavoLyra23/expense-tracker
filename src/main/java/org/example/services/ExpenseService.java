package org.example.services;

public class ExpenseService {

    private final IFileManager fileManager;

    private static final class InstanceHolder {
        private static ExpenseService instance;

        public static void initialize(IFileManager fileManager) {
            instance = new ExpenseService(fileManager);
        }

        private static ExpenseService getInstance() {
            if (instance == null) {
                throw new IllegalStateException("ExpenseService has not been initialized");
            }
            return instance;
        }
    }

    public static void initialize(IFileManager fileManager) {
        InstanceHolder.initialize(fileManager);
    }

    public static ExpenseService getInstance() {
        return InstanceHolder.getInstance();
    }

    private ExpenseService(IFileManager fileManager) {
        this.fileManager = fileManager;
    }


    public void findAll(String filePath) {
        var list = fileManager.readFile(filePath);
        list.forEach(expanse -> {
            System.out.println("# " + expanse.getId() + " " + expanse.getDate() + " " + expanse.getDescription() + " $" +
                    expanse.getAmount());
        });
    }

    public Double amountTotal(String filePath) {
        var list = fileManager.readFile(filePath);
        return list.stream().mapToDouble(expanse -> expanse.getAmount().doubleValue()).sum();
    }

    public void deleteExpense(Long id, String filePath) {
        var list = fileManager.readFile(filePath);
        boolean value = list.removeIf(expanse -> expanse.getId().equals(id));
        if (!value) {
            System.out.println("Expense not found");
            return;
        }
        fileManager.rewriteFile(list, filePath);
        System.out.println("Expense deleted successfully");
    }



}
