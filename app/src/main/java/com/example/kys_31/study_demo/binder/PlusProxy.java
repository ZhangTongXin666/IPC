package com.example.kys_31.study_demo.binder;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by kys_31 on 2017/9/12.
 */

public class PlusProxy implements IPlus {

    private IBinder bingProxy;
    public PlusProxy(IBinder bingProxy){
        this.bingProxy = bingProxy;
    }

    @Override
    public int add(int a, int b) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeInterfaceToken("PLUS TWO INT");
        data.writeInt(a);
        data.writeInt(b);
        int _result = 0;
        try {
            bingProxy.transact(1,data,reply,0);
            reply.readException();
            _result = reply.readInt();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return _result;
    }

    @Override
    public IBinder asBinder() {
        return null;
    }
}
