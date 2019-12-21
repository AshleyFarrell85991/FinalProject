package com.example.test;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PetAdaptor extends RecyclerView.Adapter<PetAdaptor.MyViewHolder> {
    private Context context;
    private List<Pet> mpetlist;

    public PetAdaptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.petitem, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PetAdaptor.MyViewHolder myViewHolder, int i) {
        myViewHolder.vet.setText("Vet:"+" "+ mpetlist.get(i).getVet());
        myViewHolder.date.setText("Date:"+" "+mpetlist.get(i).getDate());
        myViewHolder.notes.setText("Visit notes:"+" "+mpetlist.get(i).getVisitsummary());

    }

    @Override
    public int getItemCount() {
        if (mpetlist == null) {
            return 0;
        }
        return mpetlist.size();

    }

    public void setTasks(List<Pet> petlist) {
        mpetlist = petlist;
        notifyDataSetChanged();
    }

    public List<Pet> getTasks() {

        return mpetlist;
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vet, date, notes;
        AppDatabase mDb;


        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDb = AppDatabase.getInstance(context);
            vet = itemView.findViewById(R.id.pet_vet);
            date = itemView.findViewById(R.id.pet_date);
            notes = itemView.findViewById(R.id.pet_notes);

//             vet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int elementId = mpetlist.get(getAdapterPosition()).getPetid();
//                    Intent i = new Intent(context, SecondPetIntake.class);
//                    i.putExtra(Constants.UPDATE_Person_Id, elementId);
//                    //       i.putExtra("phonenumber",phonenumber.get)
//                    context.startActivity(i);
//                }
//            });
//        }
        }
    }
}