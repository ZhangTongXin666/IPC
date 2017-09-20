package com.example.kys_31.study_demo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kys_31 on 2017/9/6.
 */

public class Book implements Parcelable{

    public int bookId;
    public String bookName;

    public Book(int bookId, String bookName){
        this.bookId = bookId;
        this.bookName = bookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bookId);
        parcel.writeString(bookName);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        @Override
        public Book[] newArray(int i) {
            return new Book[1];
        }
    };

    private Book(Parcel parcel){
        bookId = parcel.readInt();
        bookName = parcel.readString();
    }

}
