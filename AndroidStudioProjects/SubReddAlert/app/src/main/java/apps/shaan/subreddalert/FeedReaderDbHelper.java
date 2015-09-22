package apps.shaan.subreddalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteProgram;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shaan on 6/24/15.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Subreddits";

    private static final int DATABASE_VERSION = 2;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE Subreddits ( subredditID INTEGER PRIMARY KEY,subreddit TEXT, post TEXT,rank INTEGER)" ;


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract.FeedEntry.TABLE_NAME;

    // Database creation sql statement
    //private static final String DATABASE_CREATE = "create table Subreddits";
    //( _id integer primary key,name text not null);";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void insertSub(HashMap<String,String> subTable){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("subreddit",subTable.get("subreddit"));
        values.put("rank",subTable.get("rank"));
        db.insert("Subreddits",null,values);
        db.close();
    }
    public int updateSub(HashMap<String,String> subTable){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("Subreddit",subTable.get("Subreddit"));
        db.insert("post",null,values);
        db.insert("Subreddit",null,values);

        db.close();
       return db.update("Subreddits", values, "subredditID" + " = ?", new String[] { subTable.get("subredditID") });

    }
    public void deleteSub(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM  Subreddits where subredditID='"+ id +"'";
        database.execSQL(deleteQuery);

    }


    public HashMap<String, String> getSubInfo(String id) {
        HashMap<String, String> subredditMap = new HashMap<String, String>();

        // Open a database for reading only

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM contacts where subredditID='"+id+"'";

        // rawQuery executes the query and returns the result as a Cursor

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                subredditMap.put("subreddit", cursor.getString(1));
                subredditMap.put("post", cursor.getString(2));
;

            } while (cursor.moveToNext());
        }
        return subredditMap;
    }

    public ArrayList<HashMap<String, String>> getAllSubreddits() {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> subredditArrayList;

        subredditArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT  * FROM subreddits";

        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();

        // Cursor provides read and write access for the
        // data returned from a database query

        // rawQuery executes the query and returns the result as a Cursor

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Move to the first row

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contactMap = new HashMap<String, String>();

                // Store the key / value pairs in a HashMap
                // Access the Cursor data by index that is in the same order
                // as used when creating the table

                contactMap.put("subredditID", cursor.getString(0));
                contactMap.put("post", cursor.getString(1));
                contactMap.put("rank", cursor.getString(2));



                subredditArrayList.add(contactMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }

        // return contact list
        return subredditArrayList;
    }
}
