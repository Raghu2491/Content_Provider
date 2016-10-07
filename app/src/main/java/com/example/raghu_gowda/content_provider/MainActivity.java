package com.example.raghu_gowda.content_provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText userName, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.username);
        address = (EditText) findViewById(R.id.address);
    }

    public void deleteUser(View view) {
    }

    public void updateUserAddress(View view) {
    }

    public void updateUser(View view) {
    }

    public void getUserId(View view) {
    }

    public void getAddress(View view) {
    }

    public void viewDetails(View view) {
        Cursor cursor=getContentResolver().query(UsersProvider.CONTENT_URI,null,null,null,null);

        while(cursor.moveToNext()){
            Toast.makeText(this,
                    cursor.getString(cursor.getColumnIndex(UsersProvider.NAME))
                            + " , "+
                            cursor.getString(cursor.getColumnIndex(UsersProvider.ADDRESS))
                    ,Toast.LENGTH_LONG).show();
        }

    }

    public void addUser(View view) {
        ContentValues values=new ContentValues();
        values.put(UsersProvider.NAME,userName.getText().toString());
        values.put(UsersProvider.ADDRESS,address.getText().toString());
        Uri uri=getContentResolver().insert(UsersProvider.CONTENT_URI,values);

        Toast.makeText(this,uri.toString(),Toast.LENGTH_LONG).show();
    }
}
