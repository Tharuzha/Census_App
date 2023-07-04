package com.example.first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.prefs.Preferences;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Homescreen extends AppCompatActivity {

    ConstraintLayout mLayout;
    int mDefaultColor;
    Button mPreferences;
    Button Add_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        selectBgColor();

        mLayout = (ConstraintLayout) findViewById(R.id.layout);
        mDefaultColor = ContextCompat.getColor(Homescreen.this, com.google.android.material.R.color.design_default_color_primary);
        Add_data = (Button) findViewById(R.id.Add_data);
        Add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homescreen.this, DataScreen.class);
                startActivity(intent);
            }
        });
        mPreferences = (Button) findViewById(R.id.Preferences);
        mPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences();
            }
        });

    }

    public void Preferences() {
        AmbilWarnaDialog Preferences = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                saveBgColor();
                mLayout.setBackgroundColor(mDefaultColor);
            }
        });
        Preferences.show();
    }

    public void saveBgColor() {
        SharedPreferences sharedPref = getSharedPreferences("census", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("bgColor", mDefaultColor);
        editor.apply();
    }

    public void selectBgColor() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("census", Context.MODE_PRIVATE);
        mDefaultColor = sharedPref.getInt("bgColor", -132097);
        ConstraintLayout Layout = findViewById(R.id.layout);
        Layout.setBackgroundColor(mDefaultColor);
    }
}