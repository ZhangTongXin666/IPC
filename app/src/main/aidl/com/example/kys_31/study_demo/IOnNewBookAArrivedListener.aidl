// IOnNewBookAArrivedListener.aidl
package com.example.kys_31.study_demo;

// Declare any non-default types here with import statements
import com.example.kys_31.study_demo.Book;
interface IOnNewBookAArrivedListener {
  void onNewBookArrived(in Book newBook);
}
