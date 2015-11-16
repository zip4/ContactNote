package com.example.pashulya.contactnote;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pashulya on 15.11.2015.
 */

public class SearchJavascriptInterface {
    public static final int NAME_COLUMN = 1;
    public static final int PHONE_COLUMN = 2;

    private Context context;

    public SearchJavascriptInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
     public String getContactsByName(String name, int limit, int offset) throws JSONException {
        SQLiteDatabase db = new ContactDBHelper(context).getReadableDatabase();

        Cursor countPeopleCursor = db.rawQuery("SELECT count(_id) FROM people WHERE `name` LIKE '" + name + "%'", null);
        countPeopleCursor.moveToFirst();
        int countPeople = countPeopleCursor.getInt(0);
        countPeopleCursor.close();

        Cursor peopleCursor = db.rawQuery("SELECT _id, name, phone FROM people WHERE `name` LIKE '" + name + "%' ORDER BY name, phone LIMIT " + limit + " OFFSET " + offset, null);

        List<Map<String, String>> results = new ArrayList<Map<String,String>>();
        while (peopleCursor.moveToNext()) {
            Map<String, String> entry = new HashMap<>();
            entry.put("name", peopleCursor.getString(NAME_COLUMN));
            entry.put("phone", peopleCursor.getString(PHONE_COLUMN));
            results.add(entry);
        }
        peopleCursor.close();

        return toJson(results, countPeople, limit, offset);
    }

    @JavascriptInterface
    public String getContactsByPhone(String phone, int limit, int offset) throws JSONException {
        SQLiteDatabase db = new ContactDBHelper(context).getReadableDatabase();

        Cursor countPeopleCursor = db.rawQuery("SELECT count(_id) FROM people WHERE `phone` LIKE '" + phone + "%'", null);
        countPeopleCursor.moveToFirst();
        int countPeople = countPeopleCursor.getInt(0);
        countPeopleCursor.close();

        Cursor peopleCursor = db.rawQuery("SELECT _id, name, phone FROM people WHERE `phone` LIKE '" + phone + "%' ORDER BY name, phone LIMIT " + limit + " OFFSET " + offset, null);

        List<Map<String, String>> results = new ArrayList<Map<String,String>>();
        while (peopleCursor.moveToNext()) {
            Map<String, String> entry = new HashMap<>();
            entry.put("name", peopleCursor.getString(NAME_COLUMN));
            entry.put("phone", peopleCursor.getString(PHONE_COLUMN));
            results.add(entry);
        }
        peopleCursor.close();

        return toJson(results, countPeople, limit, offset);
    }

    @JavascriptInterface
    public String getContactsByNameAndPhone(String name, String phone, int limit, int offset) throws JSONException {
        SQLiteDatabase db = new ContactDBHelper(context).getReadableDatabase();

        Cursor countPeopleCursor = db.rawQuery("SELECT count(_id) FROM people WHERE `name` LIKE '" + name + "%' AND `phone` LIKE '" + phone + "%'", null);
        countPeopleCursor.moveToFirst();
        int countPeople = countPeopleCursor.getInt(0);
        countPeopleCursor.close();

        Cursor peopleCursor = db.rawQuery("SELECT _id, name, phone FROM people WHERE `name` LIKE '" + name + "%' AND `phone` LIKE '" + phone + "%' ORDER BY name, phone LIMIT " + limit + " OFFSET " + offset, null);

        List<Map<String, String>> results = new ArrayList<Map<String,String>>();
        while (peopleCursor.moveToNext()) {
            Map<String, String> entry = new HashMap<>();
            entry.put("name", peopleCursor.getString(NAME_COLUMN));
            entry.put("phone", peopleCursor.getString(PHONE_COLUMN));
            results.add(entry);
        }
        peopleCursor.close();

        return toJson(results, countPeople, limit, offset);
    }

    public String toJson(List<Map<String, String>> items, int countPeople, int limit, int offset) throws JSONException {
        List<JSONObject> jsonObjects = new ArrayList<>(items.size());
        for (Map<String, String> item : items) {
            jsonObjects.add(new JSONObject(item));
        }

        JSONObject result = new JSONObject();
        result.put("items", new JSONArray(jsonObjects));
        result.put("total", countPeople);
        result.put("limit", limit);
        result.put("offset", offset);
        return result.toString();
    }
}
