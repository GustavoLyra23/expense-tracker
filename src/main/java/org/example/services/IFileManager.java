package org.example.services;

import org.example.models.Expanse;

import java.util.List;

public interface IFileManager {

    Long addExpense(Expanse expanse, String filePath);
    List<Expanse> readFile(String filePath);

    void rewriteFile(List<Expanse> expenses, String filePath);

}
