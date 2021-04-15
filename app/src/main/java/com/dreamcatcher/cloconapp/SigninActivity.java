package com.dreamcatcher.cloconapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        final Button signinButton = findViewById(R.id.signin_button);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == signinButton){
                    Toast toast = Toast.makeText(SigninActivity.this, "Welcome!", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}