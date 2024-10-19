package org.example.services;

import org.example.models.Expanse;

import java.util.List;

public interface IFileManager {

    String createFile(String fileName, Expanse expanse);

    List<Expanse> readFile(String filePath);

}
