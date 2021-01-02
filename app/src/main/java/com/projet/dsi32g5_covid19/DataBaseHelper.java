package com.projet.dsi32g5_covid19;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Classe pour manipuler la base de données
public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Contacts table name
    private static final String TABLE_REGISTER = "register";
    public static final String KEY_ID = "id";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_lAST_NAME = "last_name";
    public static final String KEY_EMAIL_ID = "email_id";
    public static final String KEY_DATE = "date_naiss";
    public static final String KEY_PASSWORD = "password";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_REGISTER + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRST_NAME + " TEXT," + KEY_lAST_NAME
            + " TEXT," + KEY_EMAIL_ID + " TEXT,"
            + KEY_DATE + " TEXT," + KEY_PASSWORD + " TEXT " + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);

        // Create tables again
        onCreate(db);

    }

    void addRegister(RegisterData registerdata)
    // code to add the new register
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, registerdata.getfirstName()); // register first Name
        values.put(KEY_lAST_NAME, registerdata.getlastName()); // register last name
        values.put(KEY_EMAIL_ID, registerdata.getEmailId());//register email id
        values.put(KEY_DATE, registerdata.getDateNaiss());//register mobile no
        values.put(KEY_PASSWORD, registerdata.getPassword());

        // Inserting Row
        db.insert(TABLE_REGISTER, null, values);
        db.close(); // Closing database connection
    }

    //code to get a register
    public String getRegister(String username) {
        String password = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REGISTER, null, "email_id=?",
                new String[]{username}, null, null, null, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return "Not Exist";
        } else if (cursor.getCount() >= 1 && cursor.moveToFirst()) {

            password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
            cursor.close();

        }
        return password;
    }
}
