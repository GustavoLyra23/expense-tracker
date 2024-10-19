package org.example.factory;

import org.example.models.Expanse;
import org.json.JSONObject;

public class JsonFactory {

    public static JSONObject createJsonObject(Expanse expanse) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", expanse.getId());
        jsonObject.put("description", expanse.getDescription());
        jsonObject.put("amount", expanse.getAmount());
        jsonObject.put("date", expanse.getDate());
        return jsonObject;

    }


}
