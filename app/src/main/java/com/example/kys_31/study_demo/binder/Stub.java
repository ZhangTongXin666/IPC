package com.example.kys_31.study_demo.binder;

import android.os.Parcel;

/**
 * Created by kys_31 on 2017/9/12.
 */

public class Stub extends Binder {

    public static final int ADD = 1;


    @Override
    public boolean onTransact(int i, Parcel data, Parcel reply, int i1){
        switch (i){
            case Stub.ADD:
                data.enforceInterface("PLUS TWO INT");
                int _arg0;
                _arg0 = data.readInt();
                int _arg1;
                _arg1 = data.readInt();
                int _result;
                _result = this.queryLocalInterface("PLUS TWO INT").add(_arg0, _arg1);
                reply.writeNoException();
                reply.writeInt(_result);
                return true;
        }
        return super.onTransact(i, data, reply, i1);
    }

}
