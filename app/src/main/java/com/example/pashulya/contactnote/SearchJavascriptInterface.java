package com.example.pashulya.contactnote;

import android.content.Context;
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
    public String searchContact(String pattern) {
        SQLiteDatabase db = new ContactDBHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM people WHERE `name` LIKE '%" + pattern + "%'", null);
        ArrayList myArrList = new ArrayList<HashMap<String, String>>();

        String forRet = "";

        if (cursor != null){
            cursor.moveToFirst();
            int i = 0;
            Map map;
            while (cursor.moveToNext()){
                //map = new HashMap<>();
                //map.
                //map.put("Name", cursor.getString(1));
                //map.put("Phone", cursor.getString(2));

                forRet += cursor.getString(1) + "<br/>" + cursor.getString(2) + "</div><hr/>";

                //myArrList.add(map);
            }
        }


        /*
        for (Object z : myArrList) {
            forRet += "<tr><td>" + ((HashMap<String, String>)z)..toString() + "</td></tr>";
        }*/

        forRet += "";

        //return myArrList.toString();
        return forRet;
    }
}
