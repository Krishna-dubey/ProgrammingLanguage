package com.example.krishna.programminglanguages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextInputEditText user_id,password;
    Button login;
    RadioGroup radioGroup;
    DatabaseHelper mydb;
    static int status=-1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("mydata",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if(sharedPreferences.getString("log","").equals("admin")){
            Intent intent=new Intent(MainActivity.this,ListOfLanguages.class);
            intent.putExtra("Loged user",0);
            startActivity(intent);
            finish();
        }
        else if(sharedPreferences.getString("log","").equals("viewer")){
            Intent intent=new Intent(MainActivity.this,ListOfLanguages.class);
            intent.putExtra("Loged user",1);
            startActivity(intent);
            finish();
        }
        mydb=new DatabaseHelper(this);
        mydb.insertdata_Admin("admin1","krishna","admin1");
        mydb.insertdata_Admin("admin2","govind","admin2");
        mydb.insertdata_Viewer("viewer1","sushil","viewer1");
        mydb.insertdata_Viewer("viewer2","vivek","viewer2");
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        user_id=(TextInputEditText) findViewById(R.id.useridEditText);
        password=(TextInputEditText)findViewById(R.id.passwordEditText);
        login=(Button)findViewById(R.id.loginButton);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.admin){
                    status=0;
                }
                if(i==R.id.viewer){
                    status=1;
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid = user_id.getText().toString();
                String pass = password.getText().toString();
                Log.d("password", pass);
                if(userid.equals("") || pass.equals(""))
                    Toast.makeText(MainActivity.this,"Enter details properly",Toast.LENGTH_LONG).show();
                else {
                    if(status==0){
                        Cursor cursor=mydb.getAdminInfo(userid);
                        cursor.moveToFirst();
                        if(cursor==null)
                            Toast.makeText(MainActivity.this,"Invalid entry",Toast.LENGTH_LONG).show();
                       else if(userid.equals(cursor.getString(cursor.getColumnIndex(mydb.Col_11))) && pass.equals(cursor.getString(cursor.getColumnIndex(mydb.Col_13)))){

                            editor.putString("log","admin");
                            editor.apply();
                            Intent intent=new Intent(MainActivity.this,ListOfLanguages.class);
                            intent.putExtra("Loged user",status);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(MainActivity.this,"Invalid entry",Toast.LENGTH_LONG).show();
                    }
//                    else
//                        Toast.makeText(MainActivity.this,"Select appropriate user",Toast.LENGTH_LONG).show();
                    else if(status==1){
                        Cursor cursor=mydb.getViewerInfo(userid);
                        cursor.moveToFirst();
                        if(userid.equals(cursor.getString(cursor.getColumnIndex(mydb.Col_21))) && pass.equals(cursor.getString(cursor.getColumnIndex(mydb.Col_23)))){
                            editor.putString("log","viewer");
                            editor.apply();
                            Intent intent=new Intent(MainActivity.this,ListOfLanguages.class);
                            intent.putExtra("Loged user",status);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(MainActivity.this,"Invalid entry",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(MainActivity.this,"Select appropriate user",Toast.LENGTH_LONG).show();

                }
                }
        });
    }

}
