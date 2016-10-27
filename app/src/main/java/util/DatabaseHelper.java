package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vlade Ilievski on 10/27/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="showbox.db";
    public static final String DATABASE_NEWS_TABLE="news_table";
    public static final String COL_1="title";
    public static final String COL_2="link";
    public static final String COL_3="description";
    public static final String COL_4="pubDate";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table"+ DATABASE_NEWS_TABLE+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,link TEXT,description TEXT,pubDate TEXT");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+DATABASE_NEWS_TABLE);
        onCreate(db);
    }

    public boolean insertData(String title,String link,String description,String pubDate){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,title);
        contentValues.put(COL_2,link);
        contentValues.put(COL_3,description);
        contentValues.put(COL_4,pubDate);
        long result = db.insert(DATABASE_NEWS_TABLE,null,contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+DATABASE_NEWS_TABLE,null);
        return res;
    }
}
