package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText password;
    Button register;
 String prevStarted = "yes",savePassword;
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences("census", Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean(prevStarted, false)) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(prevStarted, Boolean.TRUE);
            editor.apply();
        } else {
            moveToNext();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPassword();
                moveToNext();
                Toast.makeText(getApplicationContext(),"Password Saved!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void moveToNext(){
        Intent intent = new Intent(Register.this,Login.class);
        startActivity(intent);
    }
    public void getPassword(){
        savePassword = password.getText().toString();
        SharedPreferences sharedPref = getSharedPreferences("census", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Password",savePassword);
        editor.apply();
    }
}