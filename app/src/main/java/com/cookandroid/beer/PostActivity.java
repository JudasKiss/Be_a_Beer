package com.cookandroid.beer;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference  mDatabase = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference rDatabase;
    private String userId;
    private EditText mTitle, mContents;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mTitle = findViewById(R.id.post_title_eidt);
        mContents = findViewById(R.id.post_contents_edit);

        findViewById(R.id.post_save_button).setOnClickListener(this);
        rDatabase = mDatabase.child("User").child(mAuth.getUid());
        rDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name=String.valueOf(snapshot.child("name").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override

    public void onClick(View view) {
        if(mAuth.getCurrentUser()!=null){
            Map<String,Object> data = new HashMap<>();
            data.put(FirebaseID.title,mTitle.getText().toString());
            data.put(FirebaseID.contents,mContents.getText().toString());
            data.put(FirebaseID.name,name);
            userId = mAuth.getUid();
            mDatabase.child("Beer").child("4066600601920").child("post").child(userId).setValue(data);
            finish();
        }
    }
}
