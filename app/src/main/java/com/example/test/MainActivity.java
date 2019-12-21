package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button fla;
    private RecyclerView mRecyclerView;
    private PersonAdaptor mAdapter;
    private AppDatabase mDb;
    private EditText phonesearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fla = findViewById(R.id.searchbutton);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phonesearch=findViewById(R.id.phonesearch);
        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new PersonAdaptor(this);
        mRecyclerView.setAdapter(mAdapter);
        mDb = AppDatabase.getInstance(getApplicationContext());

    }



    ////had issues getting onclick recyclerview to pass data to detial view
    public void passData(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String phonenumber = phonesearch.getText().toString();


                Intent passdata = new Intent().setClass(MainActivity.this,DetailView.class);
                passdata.putExtra("phone",phonenumber);

                startActivity(passdata);
            }
        }, 700);




    }


    public void SearchPhone(View view){
        retrieveTasks();
     passData();

    }


    private void retrieveTasks() {
        final String phonenumber = phonesearch.getText().toString();
//         Intent i = new Intent(MainActivity.this, SecondPetIntake.class);
//         i.putExtra("phone",phonenumber);
//         startActivity(i);


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Owner>persons = mDb.oDao().getownphone(phonenumber);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        mAdapter.setTasks(persons);
                    }
                });
            }
        });


    }
}