package com.mjj.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    private MySQLiteOpenHelper MySQLiteOpenHelper;

    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MySQLiteOpenHelper = new MySQLiteOpenHelper(this,"BookStore.db",null,3);
    }

    public void getDatabase(View v){
        sqLiteDatabase = MySQLiteOpenHelper.getReadableDatabase();
        Log.i("asd","打开数据库");
    }

    /**
     * public long insert (String table, String nullColumnHack, ContentValues values)
     * 添加数据，（表名，传入需要设为NULL的字段，ContentValues对象）
     */
    public void addData(View v){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name","第一行代码");
        contentValues.put("author","郭霖");
        contentValues.put("price",99.9f);
        contentValues.put("pages",800);
        sqLiteDatabase.insert("Book", null, contentValues);

        contentValues.clear();

        contentValues.put("name", "第二行代码");
        contentValues.put("author","郭霖");
        contentValues.put("price",0.1f);
        contentValues.put("pages", 100);
        sqLiteDatabase.insert("Book", null, contentValues);
        Log.i("asd", "增加数据");

    }

    public void deleteData(View v){
        //`?`是占位符，意思是删除所有pages >= 100的数据，每个问号对应1个数据
        sqLiteDatabase.delete("Book", "pages >= ?", new String[]{"100"});
        Log.i("asd", "删除数据");
    }

    /**
     * public Cursor query (String table, 表名
     * String[] columns,                  列名
     * String selection,                  指定where的约束条件
     * String[] selectionArgs,            为前一个参数中的条件提供具体值
     * String groupBy,                    指定需要分组的列
     * String having,                     对分组后的结果进一步约束，就是where
     * String orderBy)                    排序
     */
    public void selectData(View v){
        Cursor cursor = sqLiteDatabase.query("Book",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                Log.i("asd","name:"+cursor.getString(cursor.getColumnIndex("name")));
                Log.i("asd", "author:" + cursor.getString(cursor.getColumnIndex("author")));
                Log.i("asd", "price:" + cursor.getString(cursor.getColumnIndex("price")));
                Log.i("asd", "pages:" + cursor.getString(cursor.getColumnIndex("pages")));
                Log.i("asd", "-----------------------------");
            }while(cursor.moveToNext());
        }
        //不要忘记关闭
        cursor.close();
        Log.i("asd", "查询数据");
    }

    public void upData(View v){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "第三行代码");
        //`?`是占位符，意思是更新所有author = 郭霖的数据,每个问号对应1个数据
        sqLiteDatabase.update("Book", contentValues, "author=?", new String[]{"郭霖"});
        Log.i("asd", "更新数据");
    }

    /**
     *事务，保证其中的操作全部执行完成，如果有一部分未执行，则所有操作全都不生效。
     * 不要忘记结束事务
     */
    public void replaceData(View v){
        //事务开始
        sqLiteDatabase.beginTransaction();

        try{
            sqLiteDatabase.delete("Book",null,null);

            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "第四行代码");
            contentValues.put("author","事务操作");
            contentValues.put("price",0.1f);
            contentValues.put("pages", 100);
            sqLiteDatabase.insert("Book", null, contentValues);

            //事务执行成功
            sqLiteDatabase.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //结束事务
            sqLiteDatabase.endTransaction();
        }


    }



}
