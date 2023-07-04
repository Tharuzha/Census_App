package com.example.first;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.database.Cursor;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Viewdata extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> name,age,gender,img;


    DBhelper DB;

    Button uploadBtn;
    MyAdapter adapter;
    int[] id;

    private static final String TAG = "Data List";
    private FirebaseFirestore firebaseDB =  FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdata);

        selectBgColor();
        DB = new DBhelper(this);
        name = new ArrayList<>();
        age = new ArrayList<>();
        gender = new ArrayList<>();
        img = new ArrayList<>();

        recyclerView = findViewById(R.id.recycleview);
        adapter = new MyAdapter(this,name,age,gender,img);

        uploadBtn = findViewById(R.id.uploadBtn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        findGender();
        displaydata();
    }

    private void findGender() {
        recyclerView = (RecyclerView)findViewById(R.id.recycleview);
    }

    private void displaydata() {
        Cursor cursor =DB.getdata();

        if (cursor.getCount() == 0)
        {
            Toast.makeText( Viewdata.this,"No Data Exists", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {
                name.add(cursor.getString(0));
                age.add(cursor.getString(1));
                gender.add(cursor.getString(2));
                img.add(cursor.getString(3));
            }
        }
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            //            String nameText = name.getText().toString();
//            String ageText = age.getText().toString();
//            String genderText = genderRadioButton.getText().toString();
            @Override
            public void onClick(View view) {

                Cursor cursor = DB.getdata();
                if (cursor.getCount()==0) {
                    Toast.makeText(Viewdata.this, "No data entered", Toast.LENGTH_SHORT).show();
                    return;
                }else {

                    Map<String, Object> data = new HashMap<>();
                    while (cursor.moveToNext()) {
                        String username = cursor.getString(0);
                        data.put("Name", cursor.getString(0) + "\n");
                        data.put("Age", cursor.getString(1) + "\n");
                        data.put("Gender", cursor.getString(2) + "\n");
                        data.put("Profile Photo", cursor.getString(3) + "\n");


                        firebaseDB.collection("Cencus APP").document(username).set(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Viewdata.this, "Saved to cloud", Toast.LENGTH_SHORT).show();
                                        DB.cleardata();
                                        recreate();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Viewdata.this, "Error in saving to cloud!", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, e.toString());
                                    }
                                });
                    }

                }


            }
        });
    }
    public void viewImage(View v) {
        ImageView imageView = (ImageView) v;
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ImageView popupImageView = new ImageView(this);
        popupImageView.setImageBitmap(bitmap);
        PopupWindow popupWindow = new PopupWindow(popupImageView, 700,700, true);
        popupWindow.showAtLocation(v, Gravity.CENTER,0,0);}

    public void selectBgColor() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("census", Context.MODE_PRIVATE);
        int mDefaultColor = sharedPref.getInt("bgColor", -132097);
        ConstraintLayout Layout = findViewById(R.id.viewData);
        Log.d(TAG, "selectBgColor: " +mDefaultColor );
        Layout.setBackgroundColor(mDefaultColor);
    }

}