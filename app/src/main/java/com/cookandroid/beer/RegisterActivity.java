package com.cookandroid.beer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "RegisterActivity";

    private ArrayAdapter adapter;
    private Spinner spinner;

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

        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){
            if(password.equals(passwordCheck)){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("회원가입이 성공적으로 이루어졌습니다.");

                                } else {
                                    if(task.getException() != null){
                                        startToast(task.getException().toString());
                                    }
                                }
                            }
                        });

            } else{
                Toast.makeText(this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
            }
        }else{
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
