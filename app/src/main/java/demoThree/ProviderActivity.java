package demoThree;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.kys_31.study_demo.Book;
import com.example.kys_31.study_demo.User;

/**
 * Created by 老头儿 on 2017/9/18.
 * @function 这个包中讲解的是 通过ContentProvider来实现跨进程通信 ，在讲解之前需要记住一点，ContentProvider是通过Uri来实现的。
 */

public class ProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        Uri bookUri = BookProvider.BOOK_TABLE_URI;  //拿到操作书籍表的Uri
        /*想数据表中插入的值*/
        ContentValues values = new ContentValues();
        values.put("_id",3);
        values.put("name","java");

        /**
         * getContentResolver（） 返回的是ContentResolver 这里可以理解为是 我们定义的BookProvider的代理对象，对客户端进行服务，通过他我们可以调用BookProvider,因为这里是跨进程通信，所以我们不能
         * 直接定义BookProvider类对象来对 数据进行直接操作。
         */
        getContentResolver().insert(bookUri,values);//这里就是进行的插入操作，根据Uri然后指定插入的值即可完成插入操作。
        /*下面是查询操作*/
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()){
            Book book = new Book(bookCursor.getInt(0), bookCursor.getString(1));
            Log.e("TAG"," "+book.bookId+"    "+book.bookName);
        }
        bookCursor.close();//关闭游标，重中之重。

        /*同上面的插入和查询*/
        Uri userUri = BookProvider.USER_TABLE_URI;
        ContentValues values1 = new ContentValues();
        values1.put("_id", 2);
        values1.put("name", "luxi");
        values1.put("sex", 0);
        getContentResolver().insert(userUri, values1);

        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (userCursor.moveToNext()){
            User user = new User(userCursor.getInt(0), userCursor.getString(1), userCursor.getInt(2) == 0?true:false);
            Log.e("TAG", ""+user.isMale+"  "+user.userName+" "+user.userId);
        }
        bookCursor.close();

    }
}
