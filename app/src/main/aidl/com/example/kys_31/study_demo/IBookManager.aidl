// IBookManager.aidl
package com.example.kys_31.study_demo;

// Declare any non-default types here with import statements
import com.example.kys_31.study_demo.Book;
import com.example.kys_31.study_demo.IOnNewBookAArrivedListener;
interface IBookManager {
    List<Book> getBookList();
    void addBook(int bookId,String bookName);
    void registerListener(IOnNewBookAArrivedListener regiter);
    void unRegisterListener(IOnNewBookAArrivedListener unRegister);
}
