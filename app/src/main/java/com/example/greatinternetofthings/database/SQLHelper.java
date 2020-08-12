package com.example.greatinternetofthings.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.greatinternetofthings.constant.ConstantAssemble;

import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    List<String> tables;

    SQLHelper(Context context, String databaseName, List<String> tables, SQLiteDatabase.CursorFactory factory, int version){
        super(context,databaseName,factory, version);
        this.tables=tables;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for(String tableName:tables){
            String tableCreateQuery="create table "+ tableName+"(itemid int primary key,name text,iconpath text,count int);";
            sqLiteDatabase.execSQL(tableCreateQuery);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
