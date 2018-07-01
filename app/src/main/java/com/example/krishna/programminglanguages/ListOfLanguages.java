package com.example.krishna.programminglanguages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import javax.xml.datatype.Duration;

public class ListOfLanguages extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    LinearLayout bottomLayout;
    DatabaseHelper mydb;
    ArrayList<String> langName=new ArrayList<>();
    TextInputEditText langinput;
    Button save,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_languages);
        mydb=new DatabaseHelper(this);

        Cursor cursor=mydb.getLanguage();
        cursor.moveToFirst();
        do{
            Log.d("Count",String.valueOf(cursor.getCount()));
            if(cursor.getCount()==0  )
                Toast.makeText(ListOfLanguages.this,"No value",Toast.LENGTH_SHORT).show();
            else
                langName.add(cursor.getString(cursor.getColumnIndex(mydb.Col_32)));
        }while(cursor.moveToNext());


        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new CustomRecyclerViewAdapter(langName,this);
        recyclerView.setAdapter(adapter);

        logout=(Button)findViewById(R.id.logout_button);
        langinput=(TextInputEditText)findViewById(R.id.language_name_edittext);
        bottomLayout=(LinearLayout)findViewById(R.id.bottom_layout);
        save=(Button)findViewById(R.id.save_button);
        Intent i= getIntent();
        int status = i.getExtras().getInt("Loged user");
        if(status==1){
            bottomLayout.setVisibility(View.INVISIBLE);
        }
        else{
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String lang=langinput.getText().toString();
                    Log.d("input value",lang+"hello");
                    if(lang.equals(""))
                    {
                        Toast.makeText(ListOfLanguages.this,"Enter first", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                       boolean val= mydb.insertdata_Lang(lang);
                       if(val==false)
                           Toast.makeText(ListOfLanguages.this,"Not Entered",Toast.LENGTH_SHORT).show();
                       else {
                           Toast.makeText(ListOfLanguages.this, "Entered", Toast.LENGTH_SHORT).show();
                           langName.add(lang);
                           adapter.notifyItemInserted(langName.size());
                           langinput.setText("");
                       }
                    }

                }
            });

        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences("mydata",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("log","");
                editor.apply();
                MainActivity.status=-1;
                Intent intent=new Intent(ListOfLanguages.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
