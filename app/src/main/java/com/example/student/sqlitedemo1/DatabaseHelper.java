package com.example.student.sqlitedemo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by STUDENT on 02-07-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Contacts.db";
 static final String TABLE_CONTACTS = "tblcontacts";
 static final String KEY_COLUMN_ID = "id";
    static final String KEY_COLUMN_NAME = "name";
   static final String KEY_COLUMN_PH_NO = "phone_number";
    Context context;
    private static final String CREATE_TABLE_COTACTS="CREATE TABLE "+TABLE_CONTACTS+"("+
            KEY_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +KEY_COLUMN_NAME+" TEXT, "
            +KEY_COLUMN_PH_NO+" TEXT);";



    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(CREATE_TABLE_COTACTS);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int newversion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACTS);
        onCreate(sqLiteDatabase);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public boolean insert_Into_Contact(ContactModel contact)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();

       // values.put(KEY_COLUMN_ID,contact.getId());
        values.put(KEY_COLUMN_NAME,contact.getName());
        values.put(KEY_COLUMN_PH_NO,contact.getPhoneno());

        long rowid=database.insert(TABLE_CONTACTS,null,values);

        if(rowid>0)
        {
            return true;

        }
        else
        {
            return  false;
        }

    }

    Cursor getContacts()
    {
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor contacts=database.rawQuery("SELECT * FROM "+TABLE_CONTACTS,null);
        return contacts;
    }
    // code to update the single contact
    public int updateContact(ContactModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_COLUMN_PH_NO, contact.getPhoneno());
String whereclause=KEY_COLUMN_NAME+"=?";
        String whereargs[]={contact.getName()};
        // updating row
        return db.update(TABLE_CONTACTS, values,whereclause ,
               whereargs);
    }

    // Deleting single contact
    public void deleteContact(ContactModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_COLUMN_NAME + " = ?",
                new String[] { contact.getName() });
        db.close();
    }



}
