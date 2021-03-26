package com.example.projekti1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    EditText name,surname,phone,mood,web;
    Button btn;
    String id;
    PersonDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name=findViewById(R.id.un);
        surname=findViewById(R.id.us);
        phone=findViewById(R.id.up);


        mood=findViewById(R.id.um);
        web=findViewById(R.id.uw);
        dbHelper=new PersonDBHelper(Main2Activity.this);



        id=getIntent().getStringExtra("ID1");
        final int i=Integer.valueOf(id);
        Cursor cursor=dbHelper.readPhoneData(i);
        if (cursor.getCount()==0){
            Toast.makeText(Main2Activity.this,"Ska te dhena",Toast.LENGTH_SHORT);
        }
        else {
            while (cursor.moveToNext()){
                name.setText(cursor.getString(1));
                surname.setText(cursor.getString(2));
                phone.setText(cursor.getString(4));
                mood.setText(cursor.getString(3));
                web.setText(cursor.getString(5));
            }
        }

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dbHelper.updateData(id,name.getText().toString(),surname.getText().toString(),mood.getText().toString(),phone.getText().toString(),web.getText().toString());
//                Intent intent=new Intent();
//                Main2Activity.this.finish();
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.btnUp) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Perditesimi i te dhenave");
        builder.setPositiveButton("PO", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.updateData(id,name.getText().toString(),surname.getText().toString(),mood.getText().toString(),phone.getText().toString(),web.getText().toString());

                Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);


            }
        });
        builder.setNegativeButton("JO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
