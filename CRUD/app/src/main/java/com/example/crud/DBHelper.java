package com.example.crud;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "MhsCrud.db";
    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE mhs(id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT , nrp TEXT UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS mhs");
    }

    public Boolean insertData(String nama, String nrp){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("nrp", nrp);
        long result = MyDB.insert("mhs", null, contentValues);
        if(result==-1) return false;
        else return true;
    }

    @SuppressLint("Range")
    public String[] checkNama(String nama) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] result = new String[2]; // To store nama and nrp
        Cursor cursor = MyDB.rawQuery("SELECT * FROM mhs WHERE nama = ?", new String[]{nama});

        if(cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                result[0] = cursor.getString(cursor.getColumnIndex("nama"));
                result[1] = cursor.getString(cursor.getColumnIndex("nrp"));
            } else {
                result[0] = null; // Set to null if no data is found
                result[1] = null;
            }
        }

        cursor.close();
        return result;
    }

    @SuppressLint("Range")
    public String[] checkNrp(String nrp) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] result = new String[2]; // To store nama and nrp
        Cursor cursor = MyDB.rawQuery("SELECT * FROM mhs WHERE nrp = ?", new String[]{nrp});

        if(cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                result[0] = cursor.getString(cursor.getColumnIndex("nama"));
                result[1] = cursor.getString(cursor.getColumnIndex("nrp"));
            } else {
                result[0] = null; // Set to null if no data is found
                result[1] = null;
            }
        }

        cursor.close();
        return result;
    }

    public boolean updateNama(String newNama, String nrp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama", newNama);
        String whereClause = "nrp = ?";
        String[] whereArgs = {nrp};
        int rowsUpdated = db.update("mhs", values, whereClause, whereArgs);
        return rowsUpdated > 0;
    }

    public boolean deleteNrpByNama(String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "nama = ?";
        String[] whereArgs = {nama};
        int rowsDeleted = db.delete("mhs", whereClause, whereArgs);
        return rowsDeleted > 0;
    }

    @SuppressLint("Range")
    public String[] showAll(){
        ArrayList<String> data = new ArrayList<>(); // Create an ArrayList to store data
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT nama,nrp FROM mhs",null);

        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String nama = cursor.getString(cursor.getColumnIndex("nama"));
                String nrp = cursor.getString(cursor.getColumnIndex("nrp"));
                data.add(nama + "\t\t\t\t\t\t\t"+nrp);
            }
        }
        cursor.close();

        String[] dataArray = data.toArray(new String[data.size()]);
        return dataArray;
    }

}
