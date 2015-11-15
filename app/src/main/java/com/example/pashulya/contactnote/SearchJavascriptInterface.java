package com.example.pashulya.contactnote;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pashulya on 15.11.2015.
 */

public class SearchJavascriptInterface {
    private Context context;

    public SearchJavascriptInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public String getSearchResult()
    {
        SharedPreferences sPref = context.getSharedPreferences("ContactNotePrefs",Context.MODE_PRIVATE);
        return sPref.getString("Output", "");
    }

    @JavascriptInterface
    public String searchContact(String pattern, Integer page) {
        SQLiteDatabase db = new ContactDBHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM people WHERE `name` LIKE '%" + pattern + "%'", null);
        ArrayList myArrList = new ArrayList<HashMap<String, String>>();
        SharedPreferences sPref = context.getSharedPreferences("ContactNotePrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor ed = sPref.edit();

        String forRet = "";

        if (cursor != null){
            cursor.moveToFirst();
            int i = 0;
            Map map;
            while (cursor.moveToNext()){
                forRet += cursor.getString(1) + "<br/>" + cursor.getString(2) + "</div><hr/>";
            }

            /*
            int pageCount = cursor.getCount();
            if(page < pageCount)
                forRet += "<div>" + "Next" + "</div>";
            if(page > 0)
                forRet += "<div>" + "Prev" + "</div>";
                */

        }

        ed.putString("Output", forRet);
        ed.commit();


        /*
        for (Object z : myArrList) {
            forRet += "<tr><td>" + ((HashMap<String, String>)z)..toString() + "</td></tr>";
        }*/

        forRet += "";

        //return myArrList.toString();
        return forRet;
    }

    @JavascriptInterface
    public String searchContact(String pattern) {
        SQLiteDatabase db = new ContactDBHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM people WHERE `name` LIKE '%" + pattern + "%'", null);
        ArrayList myArrList = new ArrayList<HashMap<String, String>>();
        SharedPreferences sPref = context.getSharedPreferences("ContactNotePrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor ed = sPref.edit();

        String forRet = "";

        if (cursor != null){
            cursor.moveToFirst();
            int i = 0;
            Map map;
            while (cursor.moveToNext()){
                forRet += cursor.getString(1) + "<br/>" + cursor.getString(2) + "</div><hr/>";
            }

        }

        ed.putString("Output", forRet);
        ed.commit();


        /*
        for (Object z : myArrList) {
            forRet += "<tr><td>" + ((HashMap<String, String>)z)..toString() + "</td></tr>";
        }*/

        forRet += "";

        //return myArrList.toString();
        return forRet;
    }
}
