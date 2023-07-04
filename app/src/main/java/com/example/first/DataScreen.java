package com.example.first;



import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Objects;


public class DataScreen extends AppCompatActivity {

    EditText name,age;
    Button submit,view;
    Button camButton;
    ImageView imageView;
    RadioButton genderbutton;
    RadioGroup gender;
    String encodedImage;
    int mDefaultColor;
    String nameDB;
    Bitmap imageDB;
    DBhelper DB;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_screen);

        selectBgColor();



        //open camera
        imageView = findViewById(R.id.profilePhoto);
        camButton = findViewById(R.id.camBtn);

        if(ContextCompat.checkSelfPermission(DataScreen.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(DataScreen.this,
                    new String[]{Manifest.permission.CAMERA}, 101);
        }

        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,101);
            }
        });

        //data store and view

        name = (EditText) findViewById(R.id.name1);
        age = (EditText) findViewById(R.id.age1);
        submit = (Button) findViewById(R.id.submit);
        gender = (RadioGroup) findViewById(R.id.radB);
        imageView = (ImageView)findViewById(R.id.profilePhoto);
        view = (Button) findViewById(R.id.view);

        //Radio Button input

        DB = new DBhelper(this);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataScreen.this,Viewdata.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = gender.getCheckedRadioButtonId();
                genderbutton = (RadioButton) findViewById(selectedId);
                String nameTXT = name.getText().toString();
                String ageNUM = age.getText().toString();
                String genderTXT = genderbutton.getText().toString();

                System.out.println(genderTXT);

                if (selectedId == -1) {
                    System.out.println("empty");
                } else {


                    Boolean checkinsertdata = DB.insertuserdata(nameTXT,ageNUM,genderTXT,encodedImage);

                    if (checkinsertdata)
                    {
                        Toast.makeText(DataScreen.this,"Data Inserted",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(DataScreen.this,"Data not Inserted",Toast.LENGTH_SHORT).show();
                    }

                    name.getText().clear();
                    age.getText().clear();
                    gender.clearCheck();
                    imageView.setImageResource(R.drawable.baseline_person_24);


                }
            }
        });

            }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            assert data != null;
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream bytearray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytearray);
            byte[] img= bytearray.toByteArray();
            encodedImage = Base64.encodeToString(img, Base64.DEFAULT);

        }
    }
    public void selectBgColor() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("census", Context.MODE_PRIVATE);
        mDefaultColor = sharedPref.getInt("bgColor", -132097);
        ConstraintLayout Layout = findViewById(R.id.addData);
        Log.d(TAG, "selectBgColor: " +mDefaultColor );
        Layout.setBackgroundColor(mDefaultColor);
    }
}