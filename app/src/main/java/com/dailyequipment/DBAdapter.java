package com.dailyequipment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 19-Apr-18.
 */

public class DBAdapter {
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "EquipmentDB1";
    private static final String DATABASE_TABLE = "Daily_Equipment";

    public static final String KEY_ROWID = "_id";
    public static final String KEY_equipment_id = "equipment_id";
    public static final String KEY_equipment_name = "equipment_name";
    public static final String KEY_equipment_WoN = "equipment_wor";

/*    public static final String KEY_Open_date = "open_date";
    public static final String KEY_Rec_qty_date = "rec_qty_date ";
    public static final String KEY_Rec_good_qty = "rec_good_qty";
    public static final String KEY_Rec_damage_qty = "rec_damage_qty ";
    public static final String KEY_Total_aval_qty = "total_aval_qty";
    public static final String KEY_Total_cons_qty = "total_cons_qty";
    public static final String KEY_Close_qty = "close_qty";
    public static final String KEY_Close_Date = "close_date";*/


    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE =
            "create table Daily_Equipment(_id integer primary key autoincrement," +
                    " equipment_id text not null, equipment_name text not null, equipment_wor text not null);";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    //---insert a contact into the database---
    public long insertContact(String equipment_id, String equipment_name, String equipment_wor) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_equipment_id, equipment_id);
        initialValues.put(KEY_equipment_name, equipment_name);
        initialValues.put(KEY_equipment_WoN, equipment_wor);

        System.out.println("DATA Insert Successfully******");
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular items---
    public boolean deleteContact(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //-----------deletes all  row----------
    public int deleteAllRecords() {
        return db.delete(DATABASE_TABLE, null, null);
    }


    public Cursor getAllContacts() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_equipment_id, KEY_equipment_name, KEY_equipment_WoN}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[]{KEY_ROWID, KEY_equipment_id, KEY_equipment_name, KEY_equipment_WoN}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    public boolean updateContact(long rowId, String equipment_name, String equipment_wor) {
        ContentValues args = new ContentValues();
        args.put(KEY_equipment_name, equipment_name);
        args.put(KEY_equipment_WoN, equipment_wor);
        System.out.println("Update Success fully");
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor CheckTable() {
        String count = "SELECT count(*) FROM Daily_Equipment";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            System.out.println("HAve Data********" + icount);
        }
//leave
        else {
            System.out.println("No Data**********" + icount);
        }
//populate table
        return mcursor;
    }

}
