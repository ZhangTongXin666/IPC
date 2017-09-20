package demoThree;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 老头儿 on 2017/9/18.
 * @function 这个包中讲解的是 通过ContentProvider来实现跨进程通信 ，在讲解之前需要记住一点，ContentProvider是通过Uri来实现的。
 */


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "book_provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";
    private static final int VERSION_DB = 1;

    private String CREATE_TABLE_BOOK = "CREATE TABLE IF NOT EXISTS "+BOOK_TABLE_NAME +"( _id INTEGER PRIMARY KEY, name text)";
    private String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS "+USER_TABLE_NAME +"( _id INTEGER PRIMARY KEY, name text,sex int)";

    private Context mContext;

    public DbOpenHelper(Context context){
        super(context, DB_NAME, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_BOOK);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
