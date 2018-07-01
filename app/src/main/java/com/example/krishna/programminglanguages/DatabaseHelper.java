package com.example.krishna.programminglanguages;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String Database_Name="Krishna.db";
    public static final String Table1="ADMIN";
    public static final String Table2="VIEWER";
    public static final String Table3="PROLANG";
    public static final String Col_11="A_Id";
    public static final String Col_12="A_Name";
    public static final String Col_13="A_Password";
    public static final String Col_21="V_Id";
    public static final String Col_22="V_Name";
    public static final String Col_23="V_Password";
    public static final String Col_31="Lang_Id";
    public static final String Col_32="Lang_Name";
    public String createTable="create table " +Table1+ " ("+Col_11+" Text primary key,"+Col_12+" Text,"+Col_13+" Text);";
    public DatabaseHelper(Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTable);
        Log.d("AdminTable","NotCreated");
        db.execSQL("create table " +Table2+ " (V_Id Text primary key,V_Name Text,V_Password Text)");
        db.execSQL("create table " +Table3+ " (Lang_Name Text primary key)");
//        insertdata_Admin("1","krishna","a");
//        insertdata_Admin("2","govind","b");
//        insertdata_Viewer("3","sushil","c");
//        insertdata_Viewer("4","vivek","d");

}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+Table1);
        db.execSQL("Drop table if exists "+Table2);
        db.execSQL("Drop table if exists "+Table3);
        onCreate(db);
//        insertdata_Admin("1","krishna","a");
//        insertdata_Admin("2","govind","b");
//        insertdata_Viewer("3","sushil","c");
//        insertdata_Viewer("4","vivek","d");

    }

    public void insertdata_Admin(String id, String name,String pass){
        SQLiteDatabase db=  this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Col_11,id);
        contentValues.put(Col_12,name);
        contentValues.put(Col_13,pass);
        long result= db.insert(Table1,null,contentValues);
        if(result==-1)
            Log.d("AdminTable","NotCreated");
        else
            Log.d("AdminTable","TableCreated");

    }
    public void insertdata_Viewer(String id, String name,String pass){
        SQLiteDatabase db=  this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Col_21,id);
        contentValues.put(Col_22,name);
        contentValues.put(Col_23,pass);
        db.insert(Table2,null,contentValues);

    }
    public boolean insertdata_Lang(String Lang_Name){
        SQLiteDatabase db=  this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Col_32,Lang_Name);
        long result = db.insert(Table3,null,contentValues);
        if (result==-1){
            return false;
        }
        else {
            return true;
        }

    }
    public Cursor getAdminInfo(String userid){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={Col_11 , Col_13};
        String wherecol=Col_11+"=?";
        String[] check={userid};
        Cursor cr=null;
        try {
            cr = db.query(Table1,columns,wherecol,check,null,null,null);
        }catch (Exception e){
            return null;
        }
        return cr;
    }
    public Cursor getViewerInfo(String userid){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={Col_21 , Col_23};
        String wherecol=Col_21+"=?";
        String[] check={userid};
        Cursor cr = db.query(Table2,columns,wherecol,check,null,null,null);
        return cr;
    }
    public Cursor getLanguage(){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] column={Col_32};
        Cursor cr=db.query(Table3,column,null,null,null,null,null);
        return cr;
    }
    public void deleteLanguage(String lang){
        SQLiteDatabase db=  this.getWritableDatabase();
        String[] column={lang};
        String wherecol=Col_32+"=?";
        db.delete(Table3,wherecol,column);
    }
}
