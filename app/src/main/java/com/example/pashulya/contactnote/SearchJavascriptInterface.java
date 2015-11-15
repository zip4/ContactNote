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
    public String searchPrevPageContact() {
        SQLiteDatabase db = new ContactDBHelper(context).getReadableDatabase();
        SharedPreferences sPref = context.getSharedPreferences("ContactNotePrefs", Context.MODE_PRIVATE);
        String pattern = sPref.getString("Pattern", "");
        int page = sPref.getInt("Page", 0) - 1;
        Cursor cursor = db.rawQuery("SELECT * FROM people WHERE `name` LIKE '%" + pattern + "%' LIMIT 4 OFFSET " + String.valueOf(page * 4), null);
        ArrayList myArrList = new ArrayList<HashMap<String, String>>();

        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("Page", page);

        String forRet = "";

        if (cursor != null){
            cursor.moveToFirst();
            Boolean i = Boolean.FALSE;
            do{
                if (i)
                    forRet += "<div style = 'background : yellow'>" + cursor.getString(1) + "<br/>" + cursor.getString(2) + "</div>";
                else
                    forRet += "<div style = 'background : blue'>" + cursor.getString(1) + "<br/>" + cursor.getString(2) + "</div>";
                i = !i;
            }
            while (cursor.moveToNext());


            int pageCount = cursor.getCount();
            if(page < pageCount)
                forRet += "<div onClick='nextButton();'>" + "Next" + "</div>";
            if(page > 0)
                forRet += "<div onClick='prevButton();'>" + "Prev" + "</div>";

        }

        ed.putString("Output", forRet);
        ed.commit();


        forRet += "";

        //return myArrList.toString();
        return forRet;
    }

    @JavascriptInterface
    public String searchNextPageContact() {
        SQLiteDatabase db = new ContactDBHelper(context).getReadableDatabase();
        SharedPreferences sPref = context.getSharedPreferences("ContactNotePrefs", Context.MODE_PRIVATE);
        String pattern = sPref.getString("Pattern", "");
        int page = 1 + sPref.getInt("Page", 0);
        Cursor cursor = db.rawQuery("SELECT * FROM people WHERE `name` LIKE '%" + pattern + "%' LIMIT 4 OFFSET " + String.valueOf(page * 4), null);
        ArrayList myArrList = new ArrayList<HashMap<String, String>>();

        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("Page", page);

        String forRet = "";

        if (cursor != null){
            cursor.moveToFirst();
            Boolean i = Boolean.FALSE;
            do{
                if (i)
                    forRet += "<div style = 'background : yellow'>" + cursor.getString(1) + "<br/>" + cursor.getString(2) + "</div>";
                else
                    forRet += "<div style = 'background : blue'>" + cursor.getString(1) + "<br/>" + cursor.getString(2) + "</div>";
                i = !i;
            }
            while (cursor.moveToNext());


            int pageCount = cursor.getCount();
            if(page < pageCount)
                forRet += "<div onClick='nextButton();'>" + "Next" + "</div>";
            if(page > 0)
                forRet += "<div onClick='prevButton();'>" + "Prev" + "</div>";

        }

        ed.putString("Output", forRet);
        ed.commit();


        return forRet;
    }

    @JavascriptInterface
    public String searchContact(String pattern) {
        SQLiteDatabase db = new ContactDBHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM people WHERE `name` LIKE '%" + pattern + "%' LIMIT 4", null);
        ArrayList myArrList = new ArrayList<HashMap<String, String>>();
        SharedPreferences sPref = context.getSharedPreferences("ContactNotePrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("Page", 0);

        String forRet = "";

        if (cursor != null){
            cursor.moveToFirst();
            Boolean i = Boolean.FALSE;
            do{
                if (i)
                    forRet += "<div style = 'background : yellow'>" + cursor.getString(1) + "<br/>" + cursor.getString(2) + "</div>";
                else
                    forRet += "<div style = 'background : blue'>" + cursor.getString(1) + "<br/>" + cursor.getString(2) + "</div>";
                i = !i;
            }
            while (cursor.moveToNext());


            forRet += "<div>" + "Next" + "</div>";

        }

        ed.putString("Pattern", pattern);
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
