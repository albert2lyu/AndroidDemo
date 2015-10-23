package com.mjj.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 构建实例之后，再调用getReadableDatabase()或者getWirteableDatabase()，就会创建数据库。
 * 数据库文件存放在/data/data/<包名>/database/
 * 如果数据库不存在，会默认去调用onCreate()方法。
 *
 * 可用的数据类型：
 * integer  整型
 * real     浮点型
 * text     文本类型
 * blob     二进制类型
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    /**
     * 创建Book表的SQL语句
     * primary key      表示主键
     * autoincrement    表示自增长
     */
    private static final String CREATE_BOOK = "create table Book (" +
                "id integer primary key autoincrement," +
                "author text," +
                "price real," +
                "pages integer," +
                "name text," +
                "categoty_id integer)";

    private static final String CREATE_CATEGORY = "create table Category(" +
                "id integer primary key autoincrement," +
                "category_name text," +
                "category_code integer)";

    /**
     * 构造方法
     * @param context   上下文
     * @param name      数据库名
     * @param factory   自定义的Cursor,一般为null
     * @param version   数据库版本号
     */
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * 数据库不存在时执行，创建数据库
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK);
        sqLiteDatabase.execSQL(CREATE_CATEGORY);
        Log.i("asd","创建了数据库");
    }

    /**
     * 当传入的数据库版本号大于旧版本号时执行，更新数据库。
     * 用Switch去判断旧版本号，做相应操作，在switch中所有case里面都没有break,做到每个新版本的升级操作都执行了。
     * @param sqLiteDatabase
     * @param oldVersion    旧版本号
     * @param newVersion    新版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        //先删除表
//        sqLiteDatabase.execSQL("drop table if exists Book");
//        sqLiteDatabase.execSQL("drop table if exists Category");
        switch (oldVersion){
            case 1:
                //创建表
                sqLiteDatabase.execSQL(CREATE_CATEGORY);
            case 2:
                //在Book表中添加字段
                sqLiteDatabase.execSQL("alter table Book add column categoty_id integer");
            default:
        }
        Log.i("asd","更新了数据库");
    }

    /**
     * 当数据库不可写入时（如磁盘空间已满）返回的对象以只读的形式打开数据库
     * @return SQLiteDatabase 返回一个可读写的数据库对象（如果数据库已存在则直接打开，否则创建一个新的数据库）
     */
    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    /**
     * 当数据库不可写入时（如磁盘空间已满）将出现异常
     * @return SQLiteDatabase 返回一个可读写的数据库对象（如果数据库已存在则直接打开，否则创建一个新的数据库）
     */
    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
}
