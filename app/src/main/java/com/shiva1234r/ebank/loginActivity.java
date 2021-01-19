package com.shiva1234r.ebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    private static final String TAG = "loginActivity2";
    EditText mEmail;
    EditText mPassword;
    Button loginBtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }

        mEmail = (EditText)findViewById(R.id.email);
        mPassword = (EditText)findViewById(R.id.password);
        loginBtn =(Button) findViewById(R.id.loginBtn);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();


                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("email is required");
                    mEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("password is required");
                    mPassword.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    Toast.makeText(loginActivity.this, "Welcome Back!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    loginActivity.this.finish();

                                } else {
                                    Toast.makeText(loginActivity.this, "Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });


            }
        });


    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
        this.finish();
    }
}