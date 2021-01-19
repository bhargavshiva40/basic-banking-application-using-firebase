package com.shiva1234r.ebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class transferMoneyActivity extends AppCompatActivity {

    String senderUserId,receiverUserId,senderName,receiverName;
    long senderBalance,receiverBalance;
    DatabaseReference mReference = FirebaseDatabase.getInstance().getReference().child("users");
    DatabaseReference senderReference;// = FirebaseDatabase.getInstance().getReference().child("users");
    DatabaseReference receiverReference;
    Spinner userSpinner;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> spinnerUsersList;
    ArrayList<String> getSpinnerUserIdList;
    EditText enteredBalance;
    Button sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transer_money);
        getSupportActionBar().hide();
        Intent intent=getIntent();
        senderUserId = intent.getStringExtra("userId");
        senderReference = FirebaseDatabase.getInstance().getReference("users").child(senderUserId);
        receiverReference = FirebaseDatabase.getInstance().getReference().child("users");
        enteredBalance = findViewById(R.id.enteredBalance);
        sendBtn = findViewById(R.id.sendBtn);

        userSpinner = findViewById(R.id.usersSpinner);
        spinnerUsersList = new ArrayList<>();
        getSpinnerUserIdList = new ArrayList<>();
        retrieveData();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.custom_spinner_me, spinnerUsersList);
        arrayAdapter.setDropDownViewResource(R.layout.drop_down_spinner);
        userSpinner.setAdapter(arrayAdapter);

    }



    @Override
    protected void onStart() {
        super.onStart();
        senderReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderBalance = Long.parseLong(snapshot.child("balance").getValue().toString());
                senderName = snapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                receiverUserId = getSpinnerUserIdList.get(position);
                receiverName = parent.getSelectedItem().toString();
                receiverReference.child(receiverUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        receiverBalance = Long.parseLong(snapshot.child("balance").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String balance = enteredBalance.getText().toString().trim();

                if(TextUtils.isEmpty(balance) || Long.valueOf(balance)==0)
                {
                    enteredBalance.setError("Minimum amount ₹1");
                    return;
                }

                else if(receiverName==senderName)
                {
                    CustomDialog customDialog = new CustomDialog("Payment Successful");
                    customDialog.show(getSupportFragmentManager(), "example");


                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("history");
                    HistoryModel historyModel = new HistoryModel(balance,receiverName, senderName);
                    reference.push().setValue(historyModel);
                }

                else if(Long.parseLong(balance)>senderBalance)
                {
                    CustomDialog customDialog = new CustomDialog("Insufficient Balance");
                    customDialog.show(getSupportFragmentManager(), "example");
                }

                else if(senderName!=receiverName)
                {
                    senderReference.child("balance").setValue(senderBalance-(Long.parseLong(balance)));
                    DatabaseReference localReceiverReference = receiverReference.child(receiverUserId);
                    localReceiverReference.child("balance").setValue(receiverBalance+(Long.parseLong(balance)))
                    .addOnCompleteListener(transferMoneyActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            enteredBalance.getText().clear();
                            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(transferMoneyActivity.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(enteredBalance.getWindowToken(),0);
                            CustomDialog customDialog = new CustomDialog("Payment Successful");
                            customDialog.show(getSupportFragmentManager(),"example");


                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("history");
                            HistoryModel historyModel = new HistoryModel(balance,receiverName, senderName);
                            reference.push().setValue(historyModel);
                        }
                    });


                }
            }
        });
    }

    protected void retrieveData() {


        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot user:snapshot.getChildren())
                {
                    spinnerUsersList.add(user.child("name").getValue().toString());
                    getSpinnerUserIdList.add(user.child("userId").getValue().toString());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}