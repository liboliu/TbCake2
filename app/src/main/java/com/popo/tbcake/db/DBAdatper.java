package com.popo.tbcake.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by popo on 14-12-10.
 */
public class DBAdatper {

    private DBHelper dbHelper;
    private SQLiteDatabase _db;
    private final Context context;

    public DBAdatper(Context context) {

        super();
        this.context = context;
        dbHelper = new DBHelper(context, DATABASE_NAME, null, DB_VERSION);
        _db = dbHelper.getWritableDatabase();

    }
    // 数据库名
    private static final String DATABASE_NAME = "tbcake";

    //建表语句 此部分只是用户第一次安装的时候会在oncreate方法中调用
    //请注意CREATE部分不要做任何修改 所有的数据库修改都应该在 update_sql中进行
    private static final String CREATE[] = new String[] {
            "create table cake (tid  integer primary key,name varchar(200),path varch(200));",

    };

    //数据库更新升级，此部分会在onupdate方法中调用
    //请注意所有的数据库结构的修改操作都要在此处进行 否则会出现升级混乱的问题
    private static String[] UPDATE = new String[]{
    };

    // 当前数据库版本    请保证 DB_VERSION的值等于update_sql.length+1
    private static final int DB_VERSION = UPDATE.length+1;
    /**
     * 数据库创建对象
     *
     * @author popo
     *
     */
    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {

            super(context, name, factory, version);
            // TODO Auto-generated constructor stub

        }
        @Override

        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            //创建表结构
            int sql_numbers = CREATE.length;
            //执行建表语句
            for(int i=0;i<sql_numbers;i++){

                try{
                    db.execSQL(CREATE[i]);
                }catch (Exception e) {
                    // TODO: handle exception
                }

            }
            //执行升级部分 保证新装用户也可以执行到升级部分
            onUpgrade(db, 1, DB_VERSION);

        }
        /**
         * 数据库升级部分
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // TODO Auto-generated method stub
            int version = newVersion-1;
            //由于数组的长度是从0开始的 而数据库版本是从一开始 所以 i=oldVersion-1
            for(int i=oldVersion-1;i<version;i++){

                try{
                    db.execSQL(UPDATE[i]);
                }catch (Exception e) {
                    // TODO: handle exception
                }

            }
        }

    }

}