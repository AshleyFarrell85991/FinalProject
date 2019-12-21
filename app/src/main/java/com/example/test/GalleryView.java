package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;


public class GalleryView extends AppCompatActivity {
    Button fla;
    private RecyclerView mRecyclerView;
    private GalleryAdaptor mAdapter;
    private AppDatabase mDb;
    private EditText phonesearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);

        mRecyclerView = findViewById(R.id.recyclerView1);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new GalleryAdaptor(this);
        mRecyclerView.setAdapter(mAdapter);
        mDb = AppDatabase.getInstance(getApplicationContext());

    }



    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }



    private void retrieveTasks() {
//         Intent i = new Intent(MainActivity.this, SecondPetIntake.class);
//         i.putExtra("phone",phonenumber);
//         startActivity(i);


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Pet> pets = mDb.pDao().getall();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        mAdapter.setTasks(pets);
                    }
                });
            }
        });


    }
}