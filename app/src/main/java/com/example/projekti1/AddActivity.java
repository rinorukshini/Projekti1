package com.example.projekti1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText etName,etSurname,etMood,etPhone,etWeb;
    AutoCompleteTextView mood;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mood=findViewById(R.id.um);
        etName=findViewById(R.id.un);
        etSurname=findViewById(R.id.us);
        etPhone=findViewById(R.id.up);
        etWeb=findViewById(R.id.updateWeb);
        btn=findViewById(R.id.update_button);

        PersonDBHelper personDBHelper=new PersonDBHelper(AddActivity.this);
        Cursor cursor=personDBHelper.readAllData();
        if (cursor.getCount()==0){

        }

        String[] mood_String={"good","bad"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,mood_String);

        mood.setThreshold(1);
        mood.setAdapter(arrayAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPhone.getText().toString().isEmpty() || etName.getText().toString().isEmpty()|| etWeb.getText().toString().isEmpty() || etSurname.getText().toString().isEmpty() || mood.getText().toString().isEmpty()){
                    Toast.makeText(AddActivity.this,"Mbushni te gjitha!",Toast.LENGTH_SHORT);
                }
                else {
                    Intent intent=new Intent();
                    PersonDBHelper personDBHelper=new PersonDBHelper(AddActivity.this);
                    personDBHelper.addPerson(etName.getText().toString().trim(),etSurname.getText().toString().trim(),mood.getText().toString().trim(),etPhone.getText().toString().trim(),etWeb.getText().toString().trim());
                        startActivity(new Intent(AddActivity.this,MainActivity.class));
                }
            }
        });
    }
}
