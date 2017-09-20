package demoThree;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 老头儿 on 2017/9/18.
 * @function 这个包中讲解的是 通过ContentProvider来实现跨进程通信 ，在讲解之前需要记住一点，ContentProvider是通过Uri来实现的。
 */


public class BookProvider extends ContentProvider {

    public static final String AUTHORITIES = "com.example.kys_31.study_demo.BookProvider";// 在清单文件中为BookProvider定义的为一值。四大组件使用的使用都要到清单文件中声明
    public static final Uri BOOK_TABLE_URI = Uri.parse("content://"+AUTHORITIES+"/book");//书籍表的Uri
    public static final Uri USER_TABLE_URI = Uri.parse("content://"+AUTHORITIES+"/user");//用户表的Uri

    public static final int BOOK_URI_CODE = 0;//书籍表的Uri对应的 UriCode
    public static final int USER_URI_CODE = 1;//用户表的Uri对应的 UriCode

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH); //这个类时专门用于将Uri与UriCode，进行一一对应的类，类似HashMap类键值对

    private Context mContext;
    private SQLiteDatabase mDb;

    /**
     * 定义静态代码快，一次运行一次。进行Uri和UriCode匹配
     */
    static {
        uriMatcher.addURI(AUTHORITIES,"book",BOOK_URI_CODE);
        uriMatcher.addURI(AUTHORITIES,"user",USER_URI_CODE);
    }

    @Override
    public boolean onCreate() {
        Log.e("TAG","onCreate运行在进程："+Thread.currentThread().getName()); //这里我们可以看到，onCreate（）方法是运行在主线程中的
        mContext = getContext();
        mDb = new DbOpenHelper(mContext).getWritableDatabase();//拿到可读写的数据库
        initDB();//初始化数据库，在这里我们只是演戏，所以数据库的初始化等操作，放在主线程中；需要注意，操作数据库是耗时的操作，所以应该采用异步操作
        return false;
    }

    private void initDB() {
        mDb.execSQL("delete from "+DbOpenHelper.USER_TABLE_NAME); //清空用户表
        mDb.execSQL("delete from "+DbOpenHelper.BOOK_TABLE_NAME); //清空书籍表
        /*插入数据*/
        mDb.execSQL("insert into book values(0,'android');");
        mDb.execSQL("insert into book values(1,'ios');");
        mDb.execSQL("insert into book values(2,'pyshon');");
        mDb.execSQL("insert into user values(0,'jake',1);");
        mDb.execSQL("insert into user values(1,'mimi',0);");
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Log.e("TAG","query运行在线程："+Thread.currentThread().getName()); //在这里我们可以看到，对于除了onCreate（）方法外，其他方法都是运行在线程池中的。我们可以连续调用这个方法好几次，以证！
        String table = getTableName(uri);//通过传入的Uri来拿到表名
       throwError(table);//判断Uri是否正确
        return mDb.query(table,strings,s,strings1,null,null,s1,null);//数据库的查询操作
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    } //这里是专门用于根据Uri返回媒体类型的

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        String table = getTableName(uri);
        throwError(table);
       long count = mDb.insert(table,null,contentValues);
        if (count > 0){
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        String table = getTableName(uri);
        throwError(table);
        int count = mDb.delete(table, s, strings);
        if (count > 0){
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        String table = getTableName(uri);
        throwError(table);
        int count = mDb.update(table, contentValues, s, strings);
        if (count > 0){
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    /**
     * 根据Uri返回指定的表名
     * @param uri //URI
     * @return //表名
     */
    private String getTableName(Uri uri){
        String tableName = null;
        switch (uriMatcher.match(uri)){ //根据之前使用Matcher进行匹配Uri和UriCode，这里是用Matcher根据传出的Uri拿到UriCode
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:break;
        }
        return tableName;
    }

    /**
     * 判断Uri是否错误
     * @param tableName 表名
     */
    private void throwError(String tableName){
        if (tableName == null){
           throw  new IllegalArgumentException("Unsupported URI");
        }
    }
}
