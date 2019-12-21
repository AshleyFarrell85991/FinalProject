package com.example.test;

import androidx.recyclerview.widget.RecyclerView;



import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class GalleryAdaptor extends RecyclerView.Adapter<GalleryAdaptor.MyViewHolder> {
    private Context context;
    private List<Pet> mPersonList;

    private onItemClickListener mListener;
    public interface onItemClickListener{
        void onItemClick(int position);

    }

    public GalleryAdaptor(Context context) {
        this.mPersonList = mPersonList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.galleryitem, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdaptor.MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText("Name:"+" "+mPersonList.get(i).getPetname());
        myViewHolder.petvet.setText("Vet:"+ " "+mPersonList.get(i).getVet());
        myViewHolder.editImage.setImageBitmap(getPhoto(mPersonList.get(i).getImage()));


    }

    @Override
    public int getItemCount() {
        if (mPersonList == null) {
            return 0;
        }
        return mPersonList.size();

    }
    //
    public void setTasks(List<Pet> personList) {
        mPersonList = personList;
        notifyDataSetChanged();
    }

    public List<Pet> getTasks() {

        return mPersonList;
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, petvet;
        ImageView editImage;
        AppDatabase mDb;
        LinearLayout ll;


        MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            mDb = AppDatabase.getInstance(context);
            name = itemView.findViewById(R.id.pet_name1);
            petvet = itemView.findViewById(R.id.pet_vet1);
            editImage = itemView.findViewById(R.id.edit_image1);
            ll = itemView.findViewById(R.id.ll);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int elementId = mPersonList.get(getAdapterPosition()).getPetid();
                    Intent i = new Intent(context, GalleryView.class);
                    i.putExtra("id", elementId);
                    //   i.putExtra("phonenumber",mPersonList.get(elementId).getPhonenumber());
                    //      mListener.onItemClick(position);


                    context.startActivity(i);
                }
            });
        }
    }
}