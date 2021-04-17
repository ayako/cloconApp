package com.dreamcatcher.cloconapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ItemActivity extends AppCompatActivity {

    ImageView itemImageView;
    TextView itemIdText;
    EditText itemTitleText, itemDetailText, itemImageUrlText;
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

        // Data binding from MainActivity
        if(getIntent().hasExtra("id")){
            String id = getIntent().getStringExtra("id");
            String title = getIntent().getStringExtra("title");
            String detail = getIntent().getStringExtra("detail");
            String imageUrl = getIntent().getStringExtra("imageUrl");
            itemIdText.setText(id);
            itemTitleText.setText(title);
            itemDetailText.setText(detail);
            itemImageUrlText.setText(imageUrl);
            Picasso.get().load(imageUrl).into(itemImageView);
        }

        // Data Insert | Update action
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String id = itemIdText.getText().toString().trim();
                DbHelper.ItemData itemData = new DbHelper.ItemData(
                        id.isEmpty()? null : Integer.parseInt(id),
                        itemTitleText.getText().toString().trim(),
                        itemDetailText.getText().toString().trim(),
                        itemImageUrlText.getText().toString().trim()
                );
                if(id.isEmpty())
                {
                    myDb.addData(itemData);
                } else {
                    myDb.updateData(itemData);
                }

                Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}