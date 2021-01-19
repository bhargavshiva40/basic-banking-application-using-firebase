package com.shiva1234r.ebank;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setTitle("eBank");


        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            Intent intent = new Intent(this, loginActivity.class);
            startActivity(intent);
            this.finish();
        }


        recyclerView = findViewById(R.id.recyclerView);
        FirebaseRecyclerOptions<Model> options=
                new FirebaseRecyclerOptions
                        .Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), Model.class)
                        .build();
        recyclerAdapter = new RecyclerAdapter(options);
        recyclerView.setAdapter(recyclerAdapter);


    }
    @Override
    public void onStart()
    {
        super.onStart();
        recyclerAdapter.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        recyclerAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.history:
                startActivity(new Intent(getApplicationContext(), historyActivity.class));
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
                this.finish();
                return true;

            default: return super.onOptionsItemSelected(item);
        }

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
        this.finish();
    }


}