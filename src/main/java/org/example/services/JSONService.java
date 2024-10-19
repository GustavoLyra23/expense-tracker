package org.example.services;

import org.example.exceptions.FileException;
import org.example.exceptions.JsonException;
import org.example.factory.JsonFactory;
import org.example.models.Expanse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JSONService implements IFileManager {

    private static final JSONService instance = new JSONService();

    public static JSONService getInstance() {
        return instance;
    }

    private JSONService() {
    }

    @Override
    public String createFile(String fileName, Expanse expanse) {
        String fullFilePath = buildFilePath(fileName);
        File originalFile = new File(fullFilePath);
        File tempFile = new File(fullFilePath + ".tmp");

        createDirectoryIfNotExists(originalFile.getParentFile());
        JSONArray jsonArray = loadExistingData(originalFile);
        jsonArray.put(JsonFactory.createJsonObject(expanse));
        writeDataToTempFile(tempFile, jsonArray);
        commitFileTransaction(tempFile, originalFile);
        return fullFilePath;
    }

    private String buildFilePath(String fileName) {
        String userHome = System.getProperty("user.home");
        return userHome + File.separator + "myAppData" + File.separator + fileName;
    }

    private void createDirectoryIfNotExists(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            throw new FileException("Could not create directory");
        }
    }

    private JSONArray loadExistingData(File file) {
        if (!file.exists()) {
            return new JSONArray();
        }
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            return new JSONArray(content);
        } catch (IOException e) {
            throw new JsonException("Could not read existing JSON file");
        }
    }

    private void writeDataToTempFile(File tempFile, JSONArray jsonArray) {
        try (FileWriter fileWriter = new FileWriter(tempFile)) {
            fileWriter.write(jsonArray.toString(4));
        } catch (IOException e) {
            deleteFileIfExists(tempFile);
            throw new JsonException("Could not write to temporary JSON file");
        }
    }


    private void commitFileTransaction(File tempFile, File originalFile) {
        try {
            Files.move(tempFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            deleteFileIfExists(tempFile);
            throw new FileException("Could not replace original file with temporary file");
        }
    }


    private void deleteFileIfExists(File file) {
        if (file.exists() && !file.delete()) {
            throw new FileException("Could not delete temporary file");
        }
    }

    @Override
    public List<Expanse> readFile(String filePath) {
        try {
            List<Expanse> expanses = new ArrayList<>();
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                expanses.add(new Expanse(jsonObject.getLong("id"), jsonObject.getString("description"), jsonObject.getBigDecimal("amount"), LocalDate.parse(jsonObject.getString("date"))));
            }
            return expanses;
        } catch (IOException e) {
            throw new FileException("Could not read Json File");
        }
    }

}
