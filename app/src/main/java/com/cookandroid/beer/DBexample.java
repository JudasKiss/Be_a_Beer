package com.cookandroid.beer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DBexample extends AppCompatActivity {

    EditText beername;
    EditText beercountry;
    EditText beerstyle;
    EditText beerbarcode;
    Button btn_save;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbexample);

        Intent intent = getIntent();
        String barcode = intent.getStringExtra("barcode");

        beername = findViewById(R.id.input);
        beercountry = findViewById(R.id.input2);
        beerstyle =  findViewById(R.id.input3);
        beerbarcode = findViewById(R.id.input4);
        btn_save = findViewById(R.id.btn);
        beerbarcode.setText(barcode, TextView.BufferType.EDITABLE);
        //firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String beerName = beername.getText().toString();
                String beerCountry = beercountry.getText().toString();
                String style=beerstyle.getText().toString();
                String barcode=beerbarcode.getText().toString();

                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("name", beerName);
                result.put("country", beerCountry);
                result.put("beer",style);

                writeNewUser(barcode, beerName, beerCountry,style);

            }
        });
    }

    private void writeNewUser(String userId, String name, String country,String style) {
        Beer beer = new Beer(name, country, style);

        mDatabase.child("Beer").child(userId).setValue(beer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(DBexample.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(DBexample.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}