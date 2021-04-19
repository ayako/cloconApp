package com.dreamcatcher.cloconapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SigninActivity extends AppCompatActivity {

    EditText userEmailText;
    Button signinButton;
    File userInfoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Context context = getApplicationContext();
        userInfoFile = new File(context.getFilesDir(), "profile.text");

        userEmailText = findViewById(R.id.user_email);
        signinButton = findViewById(R.id.signin_button);

        // Detect when Signin Button Clicked
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == signinButton){
                    // Save email in app local (to get profile)
                    String email = userEmailText.getText().toString().trim();
                    try (FileWriter writer = new FileWriter(userInfoFile)){
                        writer.write(email);
                    }
                    catch (IOException e) {}

                    // Show Welcome Message
                    Toast toast = Toast.makeText(SigninActivity.this, "Welcome!", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

}