package com.example.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class EditActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    private Bitmap bp;
    ImageView humanimage;
    EditText edit_name, edit_phonenumber, edit_address;
    Button button,imgbutton;
    int test =1;
   int mPersonId =createID(test);
 //   String mPersonId;
    int petid;
    Intent intent;
    private AppDatabase mDb;
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            button.setText("Update");

    mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id,-1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Owner person = mDb.oDao().loadOwnerbyID(mPersonId);
                    populateUI(person);



                }
            });


        }

    }





    private void populateUI(Owner person) {

        if (person == null) {
            return;
        }

        edit_name.setText(person.getName());
        edit_phonenumber.setText(person.getPhonenumber());
        edit_address.setText(person.getPhonenumber());

    }

    private void initViews() {
        edit_name = findViewById(R.id.edit_name);
        edit_address = findViewById(R.id.edit_address);
        edit_phonenumber = findViewById(R.id.edit_phonenumber);
         imgbutton = findViewById(R.id.selectimgbutton);
        button = findViewById(R.id.button);
       humanimage=findViewById(R.id.humanimage);

        imgbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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


        final Owner person = new Owner();
      //  final int id = new Random().nextInt(100)+21;
        person.setName(edit_name.getText().toString());
               person.setPhonenumber(edit_phonenumber.getText().toString());
                person.setAddress(edit_address.getText().toString());
        person.setId(mPersonId);
       person.setOwnerimage(profileImage(bp));
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
           if (!intent.hasExtra(Constants.UPDATE_Person_Id)) {
                    mDb.oDao().createOwner(person);
        } else {
               person.setId(createID(mPersonId));
               person.setId(mPersonId);
                    mDb.oDao().updateOwner(person);

   }

//this is where to pass data
       Intent i1 = new Intent(Intent.ACTION_SENDTO);
            i1 = new Intent(EditActivity.this,PetIntake.class);

          i1.putExtra("Owner",person.getId());
          startActivity(i1);
               finish();
            }
        });
    }



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
                humanimage.setImageBitmap(bp);


            }
            else if (choosenimage ==null) {

                bp= decodeUri(choosenimage,400);
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


    ///room issue with many to one so i created randomized keys
    public int createID(int position){
        Random m = new Random();

        for(int i =1; i< 1000 ; i++) {

            position = m.nextInt(20) + i;
        }

        return position;

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