package com.example.test;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class PersonAdaptor extends RecyclerView.Adapter<PersonAdaptor.MyViewHolder> {
    private Context context;
    private List<Owner> mPersonList;

    private onItemClickListener mListener;
    public interface onItemClickListener{
        void onItemClick(int position);

    }

    public PersonAdaptor(Context context) {
        this.mPersonList = mPersonList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdaptor.MyViewHolder myViewHolder, int i) {
        Owner currentowner = mPersonList.get(i);
        myViewHolder.name.setText("Name:"+" "+mPersonList.get(i).getName());
        myViewHolder.phonenumber.setText("Phone:"+ " "+mPersonList.get(i).getPhonenumber());
        myViewHolder.address.setText("Address:"+" "+mPersonList.get(i).getAddress());
        myViewHolder.editImage.setImageBitmap(getPhoto(mPersonList.get(i).getOwnerimage()));


    }

    @Override
    public int getItemCount() {
        if (mPersonList == null) {
            return 0;
        }
        return mPersonList.size();

    }
//
 public void setTasks(List<Owner> personList) {
      mPersonList = personList;
       notifyDataSetChanged();
   }

    public List<Owner> getTasks() {

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
        TextView name, phonenumber, address;
        ImageView editImage;
        AppDatabase mDb;
        LinearLayout ll;


        MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            mDb = AppDatabase.getInstance(context);
            name = itemView.findViewById(R.id.person_name);
            address = itemView.findViewById(R.id.person_phonenumber);
            phonenumber = itemView.findViewById(R.id.person_phonenumber);
            editImage = itemView.findViewById(R.id.edit_image);
            ll = itemView.findViewById(R.id.ll);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int elementId = mPersonList.get(getAdapterPosition()).getId();
                    Intent i = new Intent(context, DetailView.class);
                    i.putExtra("id", elementId);
                //   i.putExtra("phonenumber",mPersonList.get(elementId).getPhonenumber());
              //      mListener.onItemClick(position);


                 context.startActivity(i);
                }
            });
        }
    }
}