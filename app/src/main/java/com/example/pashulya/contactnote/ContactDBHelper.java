package com.example.pashulya.contactnote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Random;

/**
 * Created by Pashulya on 14.11.2015.
 */
public class ContactDBHelper extends SQLiteOpenHelper {

    public static final String DB_CONTACTS = "contacts.db";
    public static final String TABLE_NAME = "people";

    public static final String NAME = "name";
    public static final String PHONE = "phone";

    private String[] names = {"Alex", "John", "Bob", "Ivan", "Sam", "Jack", "Bill", "Mike", "James"};
    private String[] phone_code = {"050", "063", "066", "067", "073", "093", "095", "096", "097", "098"};

    private Context context;

    public ContactDBHelper(Context context) {
        super(context, DB_CONTACTS, null, 14);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT, "
            + PHONE + " TEXT);");
        fillDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void fillDB(SQLiteDatabase db){
        for (int i = 0; i < 100; i++){
            Random random = new Random();
            String iName = names[random.nextInt(names.length)];
            String iPhone = phone_code[random.nextInt(phone_code.length)] + String.valueOf(1000000 + random.nextInt(9999999 - 1000000));
            try {
                db.execSQL("INSERT INTO " + TABLE_NAME + "(" + NAME + "," + PHONE + ") VALUES(" + "\"" + iName + "\"" + ","
                        + "\"" + iPhone + "\"" + ")");
            } catch (Exception e) {
                Log.e("ERROR", e.toString());
            }
        }
    }
}
