package com.example.projekti1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList id, name, surname, mood;

    PersonAdapter(Activity activity, Context context, ArrayList id, ArrayList name, ArrayList surname,
                  ArrayList mood){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mood = mood;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.id_txt.setText(String.valueOf(id.get(position)));
        holder.name_txt.setText(String.valueOf(name.get(position)));
        holder.surname_txt.setText(String.valueOf(surname.get(position)));
        if (mood.get(position).equals("bad")){
            holder.mood_txt.setImageResource(R.drawable.bad);
        }
        else {
            holder.mood_txt.setImageResource(R.drawable.ok);
        }

        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, showActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("name", String.valueOf(name.get(position)));
                intent.putExtra("surname", String.valueOf(surname.get(position)));
                intent.putExtra("mood", String.valueOf(mood.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mood_txt;
        TextView id_txt, name_txt, surname_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt = itemView.findViewById(R.id.book_id_txt);
            name_txt = itemView.findViewById(R.id.tv_name);
            surname_txt = itemView.findViewById(R.id.tv_surname);
            mood_txt = itemView.findViewById(R.id.mood1);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
