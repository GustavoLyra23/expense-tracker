package org.example.factory;

import org.example.exceptions.FileException;
import org.example.models.Expanse;
import org.example.services.IFileManager;
import org.json.JSONObject;

import java.util.List;

public class JsonFactory {

    public static JSONObject createJsonObject(Expanse expanse, IFileManager fileManager, String filePath) {
        List<Expanse> expenseList;
        JSONObject jsonObject = new JSONObject();
        try {
            expenseList = fileManager.readFile(filePath);
            var json = createJsonObjectWithoutId(jsonObject, expanse);
            json.put("id", expenseList.size() + 1);
            return json;
        } catch (FileException e) {
            var json = createJsonObjectWithoutId(jsonObject, expanse);
            json = json.put("id", 1);
            return json;
        }
    }

    private static JSONObject createJsonObjectWithoutId(JSONObject jsonObject, Expanse expanse) {
        jsonObject.put("description", expanse.getDescription());
        jsonObject.put("amount", expanse.getAmount());
        jsonObject.put("date", expanse.getDate());
        return jsonObject;
    }



}
