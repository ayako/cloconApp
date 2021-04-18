package com.dreamcatcher.cloconapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class ProfileActivity extends AppCompatActivity {

    ImageView profileImageView;
    Button submitButton;
    private static final int RESULT_PICK_IMAGEFILE = 1001;
    private String imageFileName;
    private File imageFile;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Context context = getApplicationContext();
        imageFileName = "profile.jpg";
        imageFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageFileName);

        profileImageView = findViewById(R.id.profile_image);
        if(imageFile.exists()){
            readProfileImage(imageFile);
        }

        // Detect Profile Image View Clicked
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
            }
        });

        // Detect Submit Button Clicked
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uri != null)
                {
                    if(isExternalStorageWritable()){
                        saveProfileImage(imageFile, imageFileName);
                    }
                }

//                finish(); //Back previous page
            }
        });

        // Detect Toolbar(menu) Clicked
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            if (resultData.getData() != null) {
                uri = resultData.getData();
                Picasso.get().load(uri).into(profileImageView);
            }
        }
    }

    public void readProfileImage(File imageFile){
        if(isExternalStorageReadable()){
            try (
                    InputStream inputStream = new FileInputStream(imageFile)
            ){
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                profileImageView.setImageBitmap(bitmap);
            }
            catch (IOException exception) { }
        }
    }

    public void saveProfileImage(File imageFile, String imageFileName){
        try(InputStream inputStream = getContentResolver().openInputStream(uri);
            FileOutputStream output = new FileOutputStream(imageFile)
        ) {
            int DEFAULT_BUFFER_SIZE = 10240 * 4;
            byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
            int len;
            while((len=inputStream.read(buf))!=-1){
                output.write(buf,0, len);
            }
            output.flush();
        }
        catch (IOException e) { }
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state));
    }
}