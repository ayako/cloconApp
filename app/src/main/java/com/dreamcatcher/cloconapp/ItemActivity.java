package com.dreamcatcher.cloconapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    ImageView itemImageView;
    TextView itemIdText;
    EditText itemTitleText;
    EditText itemDetailText;
    EditText itemImageUrlText;
    Button submitButton;

    DbHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        itemImageView = findViewById(R.id.item_image);
        itemIdText = findViewById(R.id.item_id);
        itemTitleText = findViewById(R.id.item_title);
        itemDetailText = findViewById(R.id.item_detail);
        itemImageUrlText = findViewById(R.id.item_imageUrl);
        submitButton = findViewById(R.id.submit_button);

        myDb = new DbHelper(this);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DbHelper.ItemData itemData = new DbHelper.ItemData(
                        null,
                        "my suit",
                        "good for work!",
                        "https://20210413sto.blob.core.windows.net/item/womens_suit_biz_01.jpg");
                myDb.addData(itemData);
            }
        });
    }
}