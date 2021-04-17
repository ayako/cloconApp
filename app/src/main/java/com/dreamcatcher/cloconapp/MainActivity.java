package com.dreamcatcher.cloconapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbHelper myDB;
    Adapter adapter;
    RecyclerView recyclerView;
    ArrayList<String> itemIdList, itemTitleList, itemDetailList, itemImageUrlList;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Place data into RecyclerView
        myDB = new DbHelper(MainActivity.this);
        recyclerView = findViewById(R.id.item_recyclerview);

        itemIdList = new ArrayList<>();
        itemTitleList = new ArrayList<>();
        itemDetailList = new ArrayList<>();
        itemImageUrlList = new ArrayList<>();

        Cursor cursor = myDB.readData();
        while (cursor.moveToNext()){
            itemIdList.add(cursor.getString(0));
            itemTitleList.add(cursor.getString(1));
            itemDetailList.add(cursor.getString(2));
            itemImageUrlList.add(cursor.getString(3));
        }
        adapter = new Adapter(MainActivity.this, itemIdList, itemTitleList, itemDetailList, itemImageUrlList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        // Detect Floating Menu (Add button) Clicked
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        // Detect Toolbar(menu) Clicked
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

    }
}