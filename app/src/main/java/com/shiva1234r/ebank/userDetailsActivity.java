package com.shiva1234r.ebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userDetailsActivity extends AppCompatActivity {
    TextView userName, userEmail, userPhone, userBank, userBalance;
    Button sendBtn;
    String userId;
    String name,  email, phone, bank, balance;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//setTitle("UserDetails");
        setContentView(R.layout.activity_user_details);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        userBank = findViewById(R.id.userBank);
        userBalance = findViewById(R.id.userBalance);
        sendBtn = findViewById(R.id.userTransferBalance);

        Intent intent= getIntent();
        userName.setText(intent.getStringExtra("userName"));
        userEmail.setText(intent.getStringExtra("userEmail"));
        userPhone.setText(intent.getStringExtra("userPhone"));
        userBank.setText(intent.getStringExtra("userBank"));
        userBalance.setText(intent.getStringExtra("userBalance"));
        userId = intent.getStringExtra("userId");
        //reference.child()



    }

    @Override
    protected void onResume() {
        super.onResume();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), transferMoneyActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
    }
    //    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(userDetailsActivity.this, MainActivity.class));
//        userDetailsActivity.this.finish();
//    }
}