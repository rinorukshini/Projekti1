package com.example.projekti1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

public class showActivity extends AppCompatActivity {
    ImageView phoone,web,address,mood;
    Button btn;
    TextView tvNameSurname;
    String name,surname,mood1,id;
    PersonDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        phoone=findViewById(R.id.imagePhone);
        address=findViewById(R.id.imageMap);
        web=findViewById(R.id.imageWeb);
        mood=findViewById(R.id.imageMood);
        btn=findViewById(R.id.updButton);
        tvNameSurname=findViewById(R.id.tvNameSurname);
        dbHelper=new PersonDBHelper(showActivity.this);


        //First we call this
        getAndSetIntentData();
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(showActivity.this,Main2Activity.class));
            }
        });


        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }
        tvNameSurname.setText(name + " "+surname);
        final Cursor cursor=dbHelper.readPhoneData(Integer.valueOf(id));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cursor.getCount()==0){
                    startActivity(new Intent(showActivity.this,Main2Activity.class));
                }
                else {
                    while (cursor.moveToNext()) {

                        Intent intent=new Intent(showActivity.this,Main2Activity.class);
                        intent.putExtra("ID1",cursor.getString(0));



                        startActivityForResult(intent,2);

                    }
                }

            }
        });
        phoone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cursor.getCount()==0){
                    startActivity(new Intent(Intent.ACTION_DIAL));
                }
                else {
                    while (cursor.moveToNext()) {

                        String phone1=cursor.getString(4);
                        startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse("tel: "+phone1)));
                    }
                }


            }
        });
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cursor.getCount()==0){
                            startActivity(new Intent(Intent.ACTION_VIEW));
                        }
                        else {
                            while (cursor.moveToNext()) {

                                String web1=cursor.getString(5);
                                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://"+web1)));
                            }
                        }


                    }
                });
            }
        });




    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("surname") && getIntent().hasExtra("mood")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            surname = getIntent().getStringExtra("surname");
            mood1 = getIntent().getStringExtra("mood");

            //Setting Intent Data

            if (mood1.equals("bad")){
                mood.setImageResource(R.drawable.bad);
            }
            else {
                mood.setImageResource(R.drawable.ok);
            }

            Log.d("stev", name+" "+surname+" "+mood1);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }


@Override
public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.my_menu, menu);
    return super.onCreateOptionsMenu(menu);
}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.update){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder build=new AlertDialog.Builder(this);
        build.setTitle("Fshije "+name+" prejlistes se kontakteve?");
        build.setMessage("A deshironi ta fshini ?");
        build.setPositiveButton("PO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper=new PersonDBHelper(showActivity.this);
                dbHelper.deleteOneRow(id);
                finish();
            }
        });
        build.setNegativeButton("JO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        build.create().show();
    }
}
