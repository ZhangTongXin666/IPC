package com.example.kys_31.study_demo.binder;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.io.FileDescriptor;

/**
 * Created by kys_31 on 2017/9/12.
 */

public class Binder implements IBinder {

    @Override
    public boolean transact(int i, Parcel parcel, Parcel parcel1, int i1) throws RemoteException {

        return false;
    }

    @Override
    public IPlus queryLocalInterface(String s) {

        return null;
    }

    public void attachInterface(IInterface plus,String descriptor){

    }

    public boolean onTransact(int i, Parcel data, Parcel reply, int i1){


        return false;
    }

    final class BinderProxy implements IBinder{

        @Override
        public String getInterfaceDescriptor() throws RemoteException {
            return null;
        }

        @Override
        public boolean pingBinder() {
            return false;
        }

        @Override
        public boolean isBinderAlive() {
            return false;
        }

        @Override
        public IInterface queryLocalInterface(String s) {
            return null;
        }

        @Override
        public void dump(FileDescriptor fileDescriptor, String[] strings) throws RemoteException {

        }

        @Override
        public void dumpAsync(FileDescriptor fileDescriptor, String[] strings) throws RemoteException {

        }

        @Override
        public boolean transact(int i, Parcel parcel, Parcel parcel1, int i1) throws RemoteException {
            return false;
        }

        @Override
        public void linkToDeath(DeathRecipient deathRecipient, int i) throws RemoteException {

        }

        @Override
        public boolean unlinkToDeath(DeathRecipient deathRecipient, int i) {
            return false;
        }
    }





    @Override
    public String getInterfaceDescriptor() throws RemoteException {
        return null;
    }

    @Override
    public boolean pingBinder() {
        return false;
    }

    @Override
    public boolean isBinderAlive() {
        return false;
    }

    @Override
    public void dump(FileDescriptor fileDescriptor, String[] strings) throws RemoteException {

    }

    @Override
    public void dumpAsync(FileDescriptor fileDescriptor, String[] strings) throws RemoteException {

    }

    @Override
    public void linkToDeath(DeathRecipient deathRecipient, int i) throws RemoteException {

    }

    @Override
    public boolean unlinkToDeath(DeathRecipient deathRecipient, int i) {
        return false;
    }
}
