package com.example.expensemanagementrebuild;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText editTxt_Email_SU, editTxt_Password_SU, editTxt_RePassword;
    Button btn_SignUp;
    TextView txtView_SignIn;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        editTxt_Email_SU = findViewById(R.id.editTxt_Email_SU);
        editTxt_Password_SU = findViewById(R.id.editTxt_Password_SU);
        editTxt_RePassword = findViewById(R.id.editTxt_RePassword);
        btn_SignUp = findViewById(R.id.btn_SignUp);
        txtView_SignIn = findViewById(R.id.txtView_SignIn);

        txtView_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, repassword;

                email = String.valueOf(editTxt_Email_SU.getText());
                password = String.valueOf(editTxt_Password_SU.getText());
                repassword = String.valueOf(editTxt_RePassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUpActivity.this, "Enter email !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this, "Enter password !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(repassword)) {
                    Toast.makeText(SignUpActivity.this, "Enter re-password !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.equals(password, repassword)) {
                    Toast.makeText(SignUpActivity.this, "Re-password not match !", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}