package com.example.first;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class Login extends AppCompatActivity {

    EditText password;
    Button login;
    String newPassword,savedPassword;
    int attempt = 0;
    int mDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        selectBgColor();

        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            getPassword();
            if(Objects.equals(newPassword, savedPassword)){
                moveToNext();
            }
            else{
                Toast.makeText(getApplicationContext(),"Password Incorrect!!!",Toast.LENGTH_SHORT).show();
                attempt = attempt + 1;
            }
            if(attempt == 3){
                finish();
                finishAffinity();
            }
            }
        });

    }
    public void getPassword(){
        newPassword = password.getText().toString();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("census", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        savedPassword = sharedPref.getString("Password","default");
    }
    public void moveToNext(){
        Intent intent = new Intent(Login.this,Homescreen.class);
        startActivity(intent);
    }
    public void selectBgColor() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("census", Context.MODE_PRIVATE);
        mDefaultColor = sharedPref.getInt("bgColor", -132097);
        ConstraintLayout Layout = findViewById(R.id.loginview);
       // Log.d(TAG, "selectBgColor: " +mDefaultColor );
        Layout.setBackgroundColor(mDefaultColor);
    }
}