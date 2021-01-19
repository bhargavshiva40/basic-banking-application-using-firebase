package com.shiva1234r.ebank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class historyActivity extends AppCompatActivity {
    //history fucks you
    RecyclerView recyclerView;
    HistoryRecyclerAdapter historyRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("History");

        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseRecyclerOptions<HistoryModel> options = new FirebaseRecyclerOptions.Builder<HistoryModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("history"), HistoryModel.class)
                .build();

        historyRecyclerAdapter = new HistoryRecyclerAdapter(options);
        recyclerView = findViewById(R.id.historyRecyclerView);

        recyclerView.setAdapter(historyRecyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


    }

    @Override
    public void onStart()
    {
        super.onStart();
        historyRecyclerAdapter.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        historyRecyclerAdapter.stopListening();
    }



}