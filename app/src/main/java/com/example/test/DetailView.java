package com.example.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;


import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DetailView extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PetAdaptor mAdapter;
    private AppDatabase mDb;
    private TextView petname,ownerpetname;
    private ImageView img;
    //private EditText texttst;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        petname =findViewById(R.id.petadaptorname);
        ownerpetname=findViewById(R.id.ownerpetname);
        img = findViewById(R.id.petadaptorimage);
        recyclerView = findViewById(R.id.recyclerView1);
        // texttst = findViewById(R.id.texttst);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new PetAdaptor(this);
        recyclerView.setAdapter(mAdapter);
        mDb = AppDatabase.getInstance(getApplicationContext());
        String phone = getIntent().getStringExtra("phone");
        ownerpetname.setText(phone);





    }

//    @Override
   protected void onResume() {
   super.onResume();
        retrieveTasks();
   }




    // convert from byte array to bitmap
    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }



    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

         //   String imageUrl = getIntent().getStringExtra("image_url");
            String phone = getIntent().getStringExtra("phone");

            setData(phone);
        }
    }



    private void setData( String phone){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView petadaptorname = findViewById(R.id.petadaptorname);
        petadaptorname.setText(phone);


    }

//
//    public void  addnewpet(View view){
//        Intent intent = new Intent(DetailView.this,SecondPetIntake.class);
//
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                                                        @Override
//                                                        public void run() {
//
//                                                            Intent i = getIntent();
//                                                            String phone = i.getStringExtra("phone");
//
//
//                                                            final List<Pet> pets = mDb.pDao().getAllInfo(phone);
//                                                            final Pet pet = mDb.pDao().getPetinfophone(phone);
//
//                                                            final String name = pet.getPetname();
//                                                            final byte[] petpic = pet.getImage();
//                                                            final int ownerid = pet.getOwnerid();
//                                                            final Owner owner = mDb.oDao().loadOwnerbyID(ownerid);
//                                                            Intent b = new Intent(DetailView.this, SecondPetIntake.class);
//                                                            b.putExtra("Owner",ownerid);
//                                                            startActivity(b);
//
//                                                        }
//                                                    });}
//
  private void retrieveTasks() {
      // final String phonenumber = texttst.getText().toString();
   AppExecutors.getInstance().diskIO().execute(new Runnable() {
           @Override
            public void run() {
//
//
         Intent i = getIntent();
        String phone =  i.getStringExtra("phone");
             final List<Pet>pets = mDb.pDao().getAllInfo(phone);
             final Pet pet = mDb.pDao().getPetinfophone(phone);

       final String name = pet.getPetname();
      final byte[] petpic = pet.getImage();
     final int ownerid = pet.getOwnerid();
     final Owner owner = mDb.oDao().loadOwnerbyID(ownerid);
//
   runOnUiThread(new Runnable() {
                    @Override
            public void run() {
     petname.setText("Pet's name:"+ pet.getPetname());
     ownerpetname.setText("Owner's name:"+owner.getName());
      img.setImageBitmap(getPhoto(petpic));
            mAdapter.setTasks(pets);
              }
               });
       }
    });


    }
}