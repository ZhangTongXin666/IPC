package com.example.kys_31.study_demo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kys_31 on 2017/9/14.
 */

public class CustomData implements Parcelable {

    public int id;
    public String name;

    public CustomData(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<CustomData> CREATOR = new Creator<CustomData>() {
        @Override
        public CustomData createFromParcel(Parcel parcel) {
            return new CustomData(parcel) ;
        }

        @Override
        public CustomData[] newArray(int i) {
            return new CustomData[i];
        }
    };

    private CustomData(Parcel parcel){
        id = parcel.readInt();
        name = parcel.readString();
    }
}
