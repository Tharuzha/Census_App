package com.example.first;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private Context context;
    private ArrayList name_id,age_id,gender_id,img_id;

    public MyAdapter(Context context, ArrayList name_id, ArrayList age_id, ArrayList gender_id, ArrayList img_id) {
        this.context = context;
        this.name_id = name_id;
        this.age_id = age_id;
        this.gender_id = gender_id;
        this.img_id=img_id;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_id.setText(String.valueOf(name_id.get(position)));
        holder.age_id.setText(String.valueOf(age_id.get(position)));
        holder.gender_id.setText(String.valueOf(gender_id.get(position)));
        Bitmap bitmap;
        String previouslyEncodedImage =  String.valueOf(img_id.get(position));
        if( !previouslyEncodedImage.equalsIgnoreCase("") ){
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            holder.img_id.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return name_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_id,age_id,gender_id;
        ImageView img_id;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            name_id = itemView.findViewById(R.id.textname);
            age_id = itemView.findViewById(R.id.textage);
            gender_id = itemView.findViewById(R.id.textgender);
            img_id = itemView.findViewById((R.id.img));
        }
    }
}
