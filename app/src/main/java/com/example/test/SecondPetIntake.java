package com.example.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class SecondPetIntake extends AppCompatActivity {
    private AppDatabase mDb;
Button button;
    TextView petname;
    EditText date,vet,summary;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_pet_intake);
        initViews();

    }



    private void populateUI(Pet pet) {


        if (pet == null) {
            return;
        }
        petname.setText(pet.getPetname());
        vet.setText(pet.getVet());
        date.setText(pet.getDate());
        summary.setText(pet.getVisitsummary());

    }


    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


//public void SelectImage(View view){
//    Intent intent = new Intent();
//    intent.setType("image/*");
//    intent.setAction(Intent.ACTION_GET_CONTENT);
//    startActivityForResult(Intent.createChooser(intent, "select a picture"), PICK_IMAGE);
//
//
//
//}




    private void initViews() {

        Intent intent = getIntent();
        int message = intent.getIntExtra("Owner", 1);
        //     String message = intent.getStringExtra("Owner");

        vet = findViewById(R.id.vet2);
        date = findViewById(R.id.date2);
        summary = findViewById(R.id.summary2);
        button = findViewById(R.id.savebutton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });

    }

    public void onSaveButtonClicked() {

        Intent i = getIntent();
        final int message = i.getIntExtra("Owner", 1);

        final String newdate = date.getText().toString();
        final String newvet = vet.getText().toString();
        final String newvisitsummary = summary.getText().toString();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
               final Pet pet = mDb.pDao().getPet(message);
               Pet newpet = new Pet();
               String name = pet.getPetname();
               int ownerid = message;
               int petid = pet.getPetid();
               byte [] img = pet.getImage();
               newpet.setOwnerid(ownerid);
               newpet.setImage(img);
               newpet.setVisitsummary(newvisitsummary);
               newpet.setVet(newvet);
               newpet.setDate(newdate);
               newpet.setPetid(petid);

               mDb.pDao().createPet(newpet);

                Intent gohome = new Intent(SecondPetIntake.this,HomeActivity.class);
                startActivity(gohome);
                finish();
            }
    });




}}
