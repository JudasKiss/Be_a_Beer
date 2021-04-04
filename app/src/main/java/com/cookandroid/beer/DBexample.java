package com.cookandroid.beer;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DBexample extends AppCompatActivity {

    EditText beername;
    EditText beercountry;
    Button btn_save;
    Button btn_read;
    TextView textView;
    private DatabaseReference mDatabase;
    private DatabaseReference rDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbexample);

        beername = findViewById(R.id.input);
        beercountry = findViewById(R.id.input2);
        btn_read = findViewById(R.id.btn_rd);
        btn_save = findViewById(R.id.btn);
        textView = findViewById(R.id.textView10);
        //firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();
        rDatabase = FirebaseDatabase.getInstance().getReference().child("Beer");
        String number = "1234566789";
        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rDatabase.child(number).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String data = String.valueOf(snapshot.child("상품명").getValue());
                            textView.setText(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String 상품명 = beername.getText().toString();
                String 생산지역 = beercountry.getText().toString();

                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("name", 상품명);
                result.put("country", 생산지역);

                writeNewUser("12345", 상품명, 생산지역);

            }
        });
    }

    private void writeNewUser(String userId, String name, String country) {
        Beer beer = new Beer(name, country);

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

    /*private void readUser() {
        mDatabase.child("Beer").child("000040786179").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.getValue(User.class) != null) {
                    Beer post = dataSnapshot.getValue(Beer.class);
                    textView.setText(post.toString());
                    //Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    Toast.makeText(DBexample.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }*/
}