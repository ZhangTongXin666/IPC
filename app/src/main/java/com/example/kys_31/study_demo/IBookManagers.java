package com.example.kys_31.study_demo;

import android.os.IBinder;
import android.os.IInterface;

import java.util.List;

/**
 * Created by kys_31 on 2017/9/6.
 */

public interface IBookManagers extends IInterface {
    String DESCRIPTOR = "com.example.kys_31.study_demo.IBookManagers";
    int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION+0;
    int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION+1;

    List<Book> getBookList();
    void addBook(Book book);
}
