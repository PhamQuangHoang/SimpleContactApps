package com.hoangpham.contactdemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "Contact_Manager";
    private static final String TABLE_NAME = "Contact";
    private static final String ID = "Contact_Id";
    private static final String FULL_NAME = "Contact_Full_Name";
    private static final String COMPANY = "Contact_Company";
    private static final String TITLE = "Contact_Title";
    private static final String MOBILE = "Contact_Mobile";
    private static final String EMAIL = "Contact_Email";
    private static final String CREATED_AT = "Contact_Created_At";
    private static final String AVATAR = "Contact_Avatar";

    String[] columns = {ID,FULL_NAME,COMPANY,TITLE,MOBILE,EMAIL,CREATED_AT};
    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = " CREATE TABLE " + TABLE_NAME + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                FULL_NAME + " TEXT, " +
                COMPANY + " TEXT, " +
                TITLE + " TEXT, " +
                MOBILE + " TEXT, " +
                EMAIL + " TEXT, " +
                CREATED_AT + " TEXT " + " )";
        db.execSQL(script);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public int addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FULL_NAME, contact.getmFullname());
        values.put(COMPANY, contact.getmCompany());
        values.put(TITLE, contact.getmTitle());
        values.put(MOBILE, contact.getmMobile());
        values.put(EMAIL, contact.getmEmail());
        values.put(CREATED_AT, contact.getmCreatedAt());
        db.insert(TABLE_NAME, null, values);
        db = this.getReadableDatabase();
        String id = " SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(id, null);
        if (cursor != null)
            cursor.moveToFirst();
        db.close();
        return Integer.parseInt(cursor.getString(0));
    }
    public List<Contact> getAllContacts() {
        List<Contact> contactArrayList = new ArrayList<Contact>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME,columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setmId(cursor.getInt(0));
                contact.setmFullname(cursor.getString(1));
                contact.setmCompany(cursor.getString(2));
                contact.setmTitle(cursor.getString(3));
                contact.setmMobile(cursor.getString(4));
                contact.setmEmail(cursor.getString(5));
                contact.setmCreatedAt(cursor.getString(6));
                contactArrayList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactArrayList;
    }
    public boolean deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        int check = db.delete(TABLE_NAME, ID + " = " + contact.getmId(), null);
        if(check != 0){

            db.close();
            return true;
        }else {

            db.close();
            return false;

        }

    }
    public boolean deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        int check = db.delete(TABLE_NAME, null, null);

        if(check != 0){

            db.close();
            return true;
        }else {

            db.close();
            return false;

        }

    }
    public boolean updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FULL_NAME, contact.getmFullname());
        values.put(COMPANY, contact.getmCompany());
        values.put(TITLE, contact.getmTitle());
        values.put(MOBILE, contact.getmMobile());
        values.put(EMAIL, contact.getmEmail());
        values.put(CREATED_AT, contact.getmCreatedAt());


        int check = db.update(TABLE_NAME,values,ID + " ="
                + contact.getmId(),null);
        if(check != 0){

            db.close();
            return true;
        }else {

            db.close();
            return false;

        }
    }
}