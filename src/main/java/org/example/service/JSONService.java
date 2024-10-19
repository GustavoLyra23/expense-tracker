package org.example.service;

import org.example.model.Expanse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONService implements IFilePersist {

    private static final JSONService instance = new JSONService();

    public static JSONService getInstance() {
        return instance;
    }

    private JSONService() {
    }

    @Override
    public void createFile(String fileName, Expanse expanse) {

        String userHome = System.getProperty("user.home");
        String directoryPath = userHome + File.separator + "myAppData";
        String fullFilePath = directoryPath + File.separator + fileName;

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        JSONObject newData = new JSONObject();
        newData.put("id", expanse.getId());
        newData.put("description", expanse.getDescription());
        newData.put("amount", expanse.getAmount());
        newData.put("date", expanse.getDate().toString());

        File file = new File(fullFilePath);
        JSONArray jsonArray;

        if (file.exists()) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(fullFilePath)));
                jsonArray = new JSONArray(content);

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            jsonArray = new JSONArray();
        }
        jsonArray.put(newData);

        try (FileWriter fileWriter = new FileWriter(fullFilePath)) {
            fileWriter.write(jsonArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
