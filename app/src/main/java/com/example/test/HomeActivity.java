package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeActivity extends AppCompatActivity {

    private Button searchbutton;
    private Button newaddition;
    private Button updatebutton;
    private Button gallerybutton;
    private ImageView img1;
    private TextView testText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        newaddition = (Button)findViewById(R.id.newaddition);
        updatebutton =(Button)findViewById(R.id.updatebutton);
        img1 =(ImageView)findViewById(R.id.img);
        gallerybutton =(Button)findViewById(R.id.galleryid);




    }




public void ViewGallery(View view){

    Intent viewgallery = new Intent(this,GalleryView.class);
    startActivity(viewgallery);

}





    public void SearchPets(View view ){


      Intent searchItent = new Intent(this,MainActivity.class);
       startActivity(searchItent);


    }



    public void  Newaddition(View view){


        Intent postIntent = new Intent(this,EditActivity.class);
        startActivity(postIntent);


    }







}
