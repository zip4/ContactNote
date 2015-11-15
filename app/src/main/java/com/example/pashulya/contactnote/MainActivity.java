package com.example.pashulya.contactnote;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static SQLiteDatabase db;
    ArrayList<HashMap<String, String>> myArrList;
    HashMap<String, String> map;
    ListView lv;
    Button b;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), WebViewActivity.class);
                startActivity(intent);
            }
        });

        lv = (ListView) findViewById(R.id.listView);
        myArrList = new ArrayList<HashMap<String, String>>();


        db = new ContactDBHelper(getApplicationContext()).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM people", null);

        if (cursor != null){
            cursor.moveToFirst();
            int i = 0;
            while (cursor.moveToNext()){
                map = new HashMap<>();
                map.put("Name", cursor.getString(1));
                map.put("Phone", cursor.getString(2));
                myArrList.add(map);
            }
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, myArrList,
                android.R.layout.simple_list_item_2, new String[]{"Name", "Phone"},
                new int[]{android.R.id.text1, android.R.id.text2});

        lv.setAdapter(simpleAdapter);

    }
}
