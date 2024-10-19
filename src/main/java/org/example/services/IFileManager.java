package org.example.services;

import org.example.models.Expanse;

import java.util.List;

public interface IFileManager {

    Long createFile(Expanse expanse, String filePath);
    List<Expanse> readFile(String filePath);

}
