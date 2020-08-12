package com.example.greatinternetofthings.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.greatinternetofthings.constant.ConstantAssemble;
import com.example.greatinternetofthings.datastructor.AgricultureTypeItem;

import java.util.ArrayList;
import java.util.List;

public class SQLOperator {
    SQLHelper helper;

    public SQLOperator(Context context){
        List<String> tables=new ArrayList<>();
        tables.add(ConstantAssemble.TABLE_NAME_USUALLY_USE_ITEM);
        helper=new SQLHelper(context, ConstantAssemble.DATABASE_NAME_USUALLY_USE_ITEM,tables,null,ConstantAssemble.DATABASE_VERSION);
    }

    public void StoreUsuallyUseItem(AgricultureTypeItem item,boolean ifFirstTime){
        String queryString;
        SQLiteDatabase db=helper.getWritableDatabase();
        if(ifFirstTime){
            queryString="insert into "+ConstantAssemble.TABLE_NAME_USUALLY_USE_ITEM+" values ("+item.getItemId()+",'"+item.getName()+"','"+item.getImgResource()+"',"+item.getCount()+")";
        }
        else {
            queryString="select * from "+ConstantAssemble.TABLE_NAME_USUALLY_USE_ITEM+" where "+"itemid="+item.getItemId();
            Cursor cursor=db.rawQuery(queryString,null);
            if(cursor.moveToNext()){
                int count=cursor.getInt(cursor.getColumnIndex("count"));
                queryString="update "+ConstantAssemble.TABLE_NAME_USUALLY_USE_ITEM+" set count="+(count+1)+" where "+"itemid="+item.getItemId();
            }
        }
        Log.d("TAG", "queryString: ---  "+queryString);
        db.execSQL(queryString);
        db.close();
    }

    public List<AgricultureTypeItem> GetUsuallyUseItem(){
        List<AgricultureTypeItem> items=new ArrayList<>();
        String queryString="select * from "+ConstantAssemble.TABLE_NAME_USUALLY_USE_ITEM;
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery(queryString,null);
        while(cursor.moveToNext()){
            int itemId=cursor.getInt(cursor.getColumnIndex("itemid"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String imagePath=cursor.getString(cursor.getColumnIndex("iconpath"));
            Log.d("TAG", "get item: ---  "+name);
            int count=cursor.getInt(cursor.getColumnIndex("count"));
            AgricultureTypeItem singleItem=new AgricultureTypeItem();
            singleItem.setItemId(itemId);
            singleItem.setName(name);
            singleItem.setImgResource(Integer.parseInt(imagePath));
            singleItem.setCount(count);
            items.add(singleItem);
        }
        db.close();
        return items;
    }
}
