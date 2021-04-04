package com.cookandroid.beer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "RegisterActivity";

    private ArrayAdapter adapter;
    private Spinner spinner;
    private DatabaseReference mDatabase;
    private String name;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner =(Spinner)findViewById(R.id.yearSpinner);
        adapter=ArrayAdapter.createFromResource(this,R.array.year, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner =(Spinner)findViewById(R.id.monthSpinner);
        adapter=ArrayAdapter.createFromResource(this,R.array.month, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner =(Spinner)findViewById(R.id.daySpinner);
        adapter=ArrayAdapter.createFromResource(this,R.array.day, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        findViewById(R.id.registerButton).setOnClickListener(onClickListener);
    }

    public Spinner getSpinner(int objId){ return (Spinner)findViewById(objId); }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.registerButton:
                    signUp();
            }
        }
    };

    private void signUp(){
        String email = ((EditText)findViewById(R.id.idText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.password2Text)).getText().toString();
        String name = ((EditText)findViewById(R.id.nameText)).getText().toString();

        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){
            if(name.length() > 0){
                if(password.equals(passwordCheck)){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        userId = mAuth.getUid();
                                        startToast("회원가입이 성공적으로 이루어졌습니다.");
                                        profileUpdate(userId);
                                        finish();
                                    } else {
                                        if(task.getException() != null){
                                            startToast(task.getException().toString());
                                        }
                                    }
                                }
                            });
                } else{
                    startToast("비밀번호가 일치하지 않습니다.");
                }
            }else{
                startToast("이름을 입력해주세요");
            }
        }else{
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void profileUpdate(String userId){
        name = ((EditText)findViewById(R.id.nameText)).getText().toString();

        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> docData = new HashMap<>();
        docData.put("name", name);
        docData.put("listExample", Arrays.asList(1, 2, 3));
        db.collection("users").document(userId).set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //startToast("회원정보 등록 성공");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("회원정보 등록 실패");
                    }
                });
    }

}
