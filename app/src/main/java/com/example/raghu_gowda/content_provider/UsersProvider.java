package com.example.raghu_gowda.content_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

public class UsersProvider extends ContentProvider {

    static final String PROVIDER_NAME="com.raghugowda.provider.User";
    static final String URL="content://"+PROVIDER_NAME+"/users";
    static final Uri CONTENT_URI=Uri.parse(URL);

    static final String UID="_id";
    static final String NAME="name";
    static final String ADDRESS="address";

    private SQLiteDatabase db;
    static final String DATABASE_NAME="UsersDB";
    static final String TABLE_NAME="USERS";
    static final int DATABASE_VERSION=1;
    static final String CREATE_TABLE="CREATE TABLE " + TABLE_NAME +"(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ NAME + " VARCHAR(255) ,"+ADDRESS +" VARCHAR(255));";


    private static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    @Override
    public boolean onCreate() {
        Context context=getContext();
        DatabaseHelper helper=new DatabaseHelper(context);

        db=helper.getWritableDatabase();
        return (db==null)?false:true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder=new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

        if(sortOrder==null || sortOrder==""){
            sortOrder=NAME;
        }


        Cursor cursor=queryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return "Users Database";
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long rowid=db.insert(TABLE_NAME,null,values);
        if(rowid>0){
            Uri uri1= ContentUris.withAppendedId(CONTENT_URI,rowid);
            getContext().getContentResolver().notifyChange(uri1,null);
            return uri1;
        }

        throw  new SQLException("Failed to add a recodrd to"+uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count=0;
        count=db.delete(TABLE_NAME,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count=0;
        Log.d(getClass().getName(), "updateCalled");
        count=db.update(TABLE_NAME,values,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return count;    }
}
