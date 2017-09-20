package com.example.kys_31.study_demo;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by kys_31 on 2017/9/6.
 */

public class User implements Parcelable {

    private static final long serialVersionUID = 1234567890L;

    public int userId;
    public String userName;
    public boolean isMale;

    public User(int userId, String userName, boolean isMale){
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    @Override
    public String toString(){
        super.toString();
        return userName+":"+userId+":"+isMale;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(userName);
        parcel.writeInt(isMale?1:0);
    }

    public static final Parcelable.Creator<User> CRERTOR = new Parcelable.Creator<User>(){

        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };

    private User(Parcel in){
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt() == 1;
    }


}
