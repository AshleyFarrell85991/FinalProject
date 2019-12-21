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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class PetIntake extends AppCompatActivity {
    EditText petname, vet, date, summary;
    ImageView imgview;
    TextView ownerid;
    Button button, imgbutton;
    private Bitmap bp;
    private byte[] photo;
    int mPersonId;
    int petid;
    public static final int PICK_IMAGE = 1;

    Intent intent;
    private AppDatabase mDb;

    protected void onCreate(final Bundle savedInstanceState) {


        // String test = owner.getName();
        //  button = (Button)findViewById(R.id.submitb);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_intake);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            button.setText("Update");

            mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id, 1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {


                    Pet pet = mDb.pDao().loadPetbyid(mPersonId);
                    populateUI(pet);


                }
            });


        }

    }

    private void populateUI(Pet pet) {


        if (pet == null) {
            return;
        }
        petname.setText(pet.getPetname());
        vet.setText(pet.getVet());
        date.setText(pet.getDate());
        summary.setText(pet.getVisitsummary());
        ownerid.setText( pet.getOwnerid());

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            Uri choosenimage = data.getData();

            if (choosenimage != null) {

                bp = decodeUri(choosenimage, 400);
                imgview.setImageBitmap(bp);


            }
        }
    }

    private byte[] profileImage(Bitmap b) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }


    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void initViews() {

        Intent intent = getIntent();
        int message = intent.getIntExtra("Owner", 1);
        //     String message = intent.getStringExtra("Owner");
        ownerid = findViewById(R.id.test1);
        petname = findViewById(R.id.petname);
        vet = findViewById(R.id.vet);
        date = findViewById(R.id.date);
        summary = findViewById(R.id.summary);
        button = findViewById(R.id.submitb);
        imgbutton = findViewById(R.id.imgbutton);
        imgview = findViewById(R.id.imgview);

        imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setType("image/*");
                pic.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(pic, "select a picture"), PICK_IMAGE);


            }

        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {
        final Pet pet = new Pet();

        Intent i = getIntent();
        int message = i.getIntExtra("Owner", 1);


        pet.setPetname(petname.getText().toString());
        pet.setDate(date.getText().toString());
        pet.setVet(vet.getText().toString());
        pet.setVisitsummary(summary.getText().toString());
        pet.setImage(profileImage(bp));
        pet.setOwnerid(message);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(Constants.UPDATE_Person_Id)) {

                    //         owner =(Owner) getIntent().getSerializableExtra("Owner");
                    //    Owner owner= mDb.oDao().loadOwnerbyID(ownerid.getText().toString());
                    //  pet.setOwnerid(owner.getId());
                    mDb.pDao().createPet(pet);
                } else {
                    pet.setPetid(mPersonId);
                    mDb.pDao().upDatePet(pet);
                }

             Intent gohome = new Intent(PetIntake.this,HomeActivity.class);
             startActivity(gohome);
             finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}