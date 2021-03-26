package com.example.projekti1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton btn;
    ImageView imageView;
    TextView textView;

    ArrayList<String> name,surname,mood,id;
    PersonDBHelper personDBHelper;
    PersonAdapter personAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        btn=findViewById(R.id.add_button1);
        imageView=findViewById(R.id.empty_imageview);
        textView=findViewById(R.id.no_data);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddActivity.class));
            }
        });
        textView.setVisibility(View.VISIBLE);

        personDBHelper=new PersonDBHelper(MainActivity.this);
        name=new ArrayList<>();
        surname=new ArrayList<>();
        mood=new ArrayList<>();
        id=new ArrayList<>();

        storeDataInArrays();
        personAdapter=new PersonAdapter(MainActivity.this,this,id,name,surname,mood);
        recyclerView.setAdapter(personAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }
    void storeDataInArrays(){
        Cursor cursor=personDBHelper.readAllData();
        if (cursor.getCount()==0){
            textView.setVisibility(View.VISIBLE);
        }
        else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                surname.add(cursor.getString(2));
                mood.add(cursor.getString(3));
            }
            textView.setVisibility(View.GONE);

        }
    }

}
