package com.dreamcatcher.cloconapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;


public class ProfileActivity extends AppCompatActivity {

    ImageView profileImageView;
    TextView profileIdText;
    EditText profileNameText, profileEmailText, profileIntroText, profileLocationText;
    Button submitButton;

    DbHelper myDb;
    File userInfoFile;

    private static final int RESULT_PICK_IMAGEFILE = 1001;
    private String imageFileName;
    private File imageFile;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Context context = getApplicationContext();
        myDb = new DbHelper(this);
        userInfoFile = new File(context.getFilesDir(), "profile.text");

        profileImageView = findViewById(R.id.profile_image);
        profileIdText = findViewById(R.id.profile_id);
        profileNameText = findViewById(R.id.profile_name);
        profileEmailText = findViewById(R.id.profile_email);
        profileIntroText = findViewById(R.id.profile_intro);
        profileLocationText = findViewById(R.id.profile_location);

        // Get and Set UserInfo if exists
        String email = null;
        if(userInfoFile.exists())
        {
            try( BufferedReader br = new BufferedReader(new FileReader(userInfoFile));
            ){
                email = br.readLine();
            } catch (IOException e) {}
        }

        if(email != null){
            profileEmailText.setText(email);

            Cursor cursor = myDb.readProfileData();
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex("email")).equals(email)){
                    imageFileName = cursor.getString(cursor.getColumnIndex("imageUrl"));
                    if (imageFileName != null){
                        imageFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageFileName);
                        readProfileImage(imageFile);
                    }

                    profileIdText.setText(cursor.getString(cursor.getColumnIndex("id")));
                    profileNameText.setText(cursor.getString(cursor.getColumnIndex("name")));
                    profileIntroText.setText(cursor.getString(cursor.getColumnIndex("intro")));
                    profileLocationText.setText(cursor.getString(cursor.getColumnIndex("location")));
                }
            }
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

                // Save Profile Image
                if(uri != null)
                {
                    if(imageFileName == null) {
                        imageFileName = profileEmailText.getText().toString()
                                .replace('@', '_')
                                .replace('.', '_') + ".jpg";
                    }
                    if(isExternalStorageWritable()){
                        File imageFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageFileName);
                        saveProfileImage(imageFile);
                    }
                }

                // Save Profile Data to Database
                String id = profileIdText.getText().toString().trim();
                String email = profileEmailText.getText().toString().trim();

                if (!email.matches(".*@.*")){
                    Toast.makeText(context,"Please type email correctly (it should have @)", Toast.LENGTH_LONG).show();
                } else {
                    DbHelper.ProfileData profileData = new DbHelper.ProfileData(
                            id.isEmpty()? null : Integer.parseInt(id),
                            profileNameText.getText().toString().trim(),
                            profileEmailText.getText().toString().trim(),
                            profileIntroText.getText().toString().trim(),
                            profileLocationText.getText().toString().trim(),
                            imageFileName
                    );
                    if(id.isEmpty())
                    {
                        myDb.addData(profileData);
                    } else {
                        myDb.updateData(profileData);
                    }

                    finish(); //Back previous page
                }
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
            catch (IOException e) { }
        }
    }

    public void saveProfileImage(File imageFile){
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