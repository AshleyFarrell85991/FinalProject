package com.example.test;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //OUR VIEWS
    ImageView img;
    TextView nameTxt;
    TextView posTxt;
    private ItemClickListener itemClickListener;
    TextView name, phonenumber, address;
    ImageView editImage;
    AppDatabase mDb;
    LinearLayout ll;
Context context;

    //our contructor
    public myViewHolder(View itemView) {
        super(itemView);

        mDb = AppDatabase.getInstance(context);
        name = itemView.findViewById(R.id.person_name);
        address = itemView.findViewById(R.id.person_phonenumber);
        phonenumber = itemView.findViewById(R.id.person_phonenumber);
        editImage = itemView.findViewById(R.id.edit_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;

    }
}
